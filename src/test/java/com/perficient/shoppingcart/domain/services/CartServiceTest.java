package com.perficient.shoppingcart.domain.services;

import com.perficient.shoppingcart.domain.exceptions.CartEmptyException;
import com.perficient.shoppingcart.domain.exceptions.NotExistException;
import com.perficient.shoppingcart.domain.exceptions.ProductNotAvailableException;
import com.perficient.shoppingcart.domain.repositories.ProductDomainRepository;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import com.perficient.shoppingcart.infrastructure.mother.CartItemDomainMother;
import com.perficient.shoppingcart.infrastructure.mother.ProductDomainMother;
import com.perficient.shoppingcart.infrastructure.mother.ProductIdDomainMother;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CartServiceTest {
    @InjectMocks
    private CartService cartService;

    @Mock
    private ProductDomainRepository productDomainRepository;

    @Mock
    private CartPaymentService cartPaymentService;

    @Captor
    ArgumentCaptor<ProductIdDomain> productIdDomainArgCaptor;

    @Captor
    ArgumentCaptor<Integer> quantityArgCaptor;


    @Test
    void addItemToCartSuccessfully() {
        var cartItemDomain = CartItemDomainMother.withFixValues();
        var productIdDomain = cartItemDomain.getProductIdDomain();
        var productDomain = ProductDomainMother.fromCartItem(cartItemDomain);
        var expectedStockQuantity = productDomain.getStock() - 1;
        var expectedCartQuantity = cartItemDomain.getQuantity() + 1;
        var expectedUnitPrice = cartItemDomain.getUnitPrice();

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(productDomain.getProductIdDomain().getId(), cartItemDomain);

        when(productDomainRepository.getProductById(any(ProductIdDomain.class)))
                .thenReturn(Optional.of(productDomain));

        when(productDomainRepository.getStockQuantity(any(ProductIdDomain.class)))
                .thenReturn(productDomain.getStock());

        doNothing().when(productDomainRepository).updateStockQuantity(any(ProductIdDomain.class), any(Integer.class));

        cartService.addItemToCart(productIdDomain, cartItemsDomain);

        verify(productDomainRepository).updateStockQuantity(
                productIdDomainArgCaptor.capture(), quantityArgCaptor.capture());
        verify(productDomainRepository, atLeastOnce()).getProductById(any(ProductIdDomain.class));
        verify(productDomainRepository, atLeastOnce()).updateStockQuantity(
                any(ProductIdDomain.class), any(Integer.class));

        var actualProductIdDomain = productIdDomainArgCaptor.getValue();
        var actualQuantity = quantityArgCaptor.getValue();
        assertEquals(productIdDomain.getId(), actualProductIdDomain.getId());
        assertEquals(expectedStockQuantity, actualQuantity);

        assertNotNull(cartItemsDomain);
        assertFalse(cartItemsDomain.isEmpty());
        assertTrue(cartItemsDomain.containsKey(productIdDomain.getId()));
        assertEquals(expectedCartQuantity, cartItemsDomain.get(productIdDomain.getId()).getQuantity());
        assertEquals(expectedUnitPrice, cartItemsDomain.get(productIdDomain.getId()).getUnitPrice());
    }

    @Test
    void addItemToCartSuccessfullyWithEmptyCart() {

        var productDomain = ProductDomainMother.random();
        var productIdDomain = productDomain.getProductIdDomain();
        var expectedStockQuantity =  productDomain.getStock() - 1;
        var expectedCartQuantity =  1;
        var expectedUnitPrice = productDomain.getUnitPrice();

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();

        when(productDomainRepository.getProductById(any(ProductIdDomain.class)))
                .thenReturn(Optional.of(productDomain));

        when(productDomainRepository.getStockQuantity(any(ProductIdDomain.class)))
                .thenReturn(productDomain.getStock());

        doNothing().when(productDomainRepository).updateStockQuantity(any(ProductIdDomain.class), any(Integer.class));

        cartService.addItemToCart(productIdDomain, cartItemsDomain);

        verify(productDomainRepository, atLeastOnce()).getProductById(any(ProductIdDomain.class));
        verify(productDomainRepository, atLeastOnce()).updateStockQuantity(any(ProductIdDomain.class), any(Integer.class));
        verify(productDomainRepository).updateStockQuantity(productIdDomainArgCaptor.capture(),
                quantityArgCaptor.capture());

        var actualQuantity = quantityArgCaptor.getValue();
        var actualProductIdDomain = productIdDomainArgCaptor.getValue();
        assertEquals(expectedStockQuantity, actualQuantity);
        assertEquals(productIdDomain.getId(), actualProductIdDomain.getId());

        assertNotNull(cartItemsDomain);
        assertFalse(cartItemsDomain.isEmpty());
        assertTrue(cartItemsDomain.containsKey(productIdDomain.getId()));
        assertEquals(expectedCartQuantity, cartItemsDomain.get(productIdDomain.getId()).getQuantity());
        assertEquals(expectedUnitPrice, cartItemsDomain.get(productIdDomain.getId()).getUnitPrice());
    }

    @Test
    void addItemToCartWhenProductNotExist() {
        var productIdDomain = ProductIdDomainMother.random();
        var cartItemDomain = CartItemDomainMother.random();

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(cartItemDomain.getProductIdDomain().getId(), cartItemDomain);

        when(productDomainRepository.getProductById(any(ProductIdDomain.class)))
                .thenReturn(Optional.empty());

        assertThrows(NotExistException.class, () -> cartService.addItemToCart(productIdDomain, cartItemsDomain));
    }

    @Test
    void addItemToCartWhenProductNotAvailable() {
        var productIdDomain = ProductIdDomainMother.random();
        var productDomain = ProductDomainMother.noStockAvailable();
        var cartItemDomain = CartItemDomainMother.random();

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(cartItemDomain.getProductIdDomain().getId(), cartItemDomain);

        when(productDomainRepository.getProductById(any(ProductIdDomain.class)))
                .thenReturn(Optional.of(productDomain));

        assertThrows(ProductNotAvailableException.class,
                () -> cartService.addItemToCart(productIdDomain, cartItemsDomain));
    }

    @Test
    void deleteItemFromCartSuccessfully() {
        var cartItemDomain = CartItemDomainMother.withFixValues();
        var productDomain = ProductDomainMother.fromCartItem(cartItemDomain);
        var productIdDomain = cartItemDomain.getProductIdDomain();
        var expectedStockQuantity = productDomain.getStock() + 1;
        var expectedCartQuantity = cartItemDomain.getQuantity() - 1;
        var expectedUnitPrice = cartItemDomain.getUnitPrice();


        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(productDomain.getProductIdDomain().getId(), cartItemDomain);

        when(productDomainRepository.getStockQuantity(any(ProductIdDomain.class)))
                .thenReturn(productDomain.getStock());

        doNothing().when(productDomainRepository).updateStockQuantity(any(ProductIdDomain.class), any(Integer.class));

        cartService.deleteItemFromCart(productIdDomain, cartItemsDomain);

        verify(productDomainRepository, atLeastOnce()).updateStockQuantity(any(ProductIdDomain.class), any(Integer.class));
        verify(productDomainRepository).updateStockQuantity(productIdDomainArgCaptor.capture(), quantityArgCaptor.capture());

        var actualQuantity = quantityArgCaptor.getValue();
        var actualProductIdDomain = productIdDomainArgCaptor.getValue();
        assertEquals(expectedStockQuantity, actualQuantity);
        assertEquals(productIdDomain.getId(), actualProductIdDomain.getId());

        assertNotNull(cartItemsDomain);
        assertFalse(cartItemsDomain.isEmpty());
        assertTrue(cartItemsDomain.containsKey(productIdDomain.getId()));
        assertEquals(expectedCartQuantity, cartItemsDomain.get(productIdDomain.getId()).getQuantity());
        assertEquals(expectedUnitPrice, cartItemsDomain.get(productIdDomain.getId()).getUnitPrice());

    }

    @Test
    void deleteItemFromCartRemoveProduct() {
        var cartItemDomain = CartItemDomainMother.onlyOneInStocks();
        var productDomain = ProductDomainMother.fromCartItem(cartItemDomain);
        var productIdDomain = new ProductIdDomain(productDomain.getProductIdDomain().getId());
        var expectedStockQuantity = productDomain.getStock() + 1;

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(productDomain.getProductIdDomain().getId(), cartItemDomain);

        when(productDomainRepository.getStockQuantity(any(ProductIdDomain.class)))
                .thenReturn(productDomain.getStock());

        doNothing().when(productDomainRepository).updateStockQuantity(any(ProductIdDomain.class), any(Integer.class));

        cartService.deleteItemFromCart(productIdDomain, cartItemsDomain);

        verify(productDomainRepository, atLeastOnce()).updateStockQuantity(any(ProductIdDomain.class), any(Integer.class));
        verify(productDomainRepository).updateStockQuantity(productIdDomainArgCaptor.capture(), quantityArgCaptor.capture());

        var actualQuantity = quantityArgCaptor.getValue();
        var actualProductIdDomain = productIdDomainArgCaptor.getValue();
        assertEquals(expectedStockQuantity, actualQuantity);
        assertEquals(productIdDomain.getId(), actualProductIdDomain.getId());

        assertTrue(cartItemsDomain.isEmpty());
    }

    @Test
    void deleteItemFromCartWhenEmptyCart() {
        var productDomain = ProductDomainMother.random();
        var productIdDomain = new ProductIdDomain(productDomain.getProductIdDomain().getId());

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();

        assertThrows(CartEmptyException.class,
                () -> cartService.deleteItemFromCart(productIdDomain, cartItemsDomain));

    }

    @Test
    void deleteAllItemFromCart() {
        var cartItemDomain = CartItemDomainMother.twoOrMoreInStock();
        var productDomain = ProductDomainMother.fromCartItem(cartItemDomain);
        var expectedStockQuantity = productDomain.getStock() + cartItemDomain.getQuantity();

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(productDomain.getProductIdDomain().getId(), cartItemDomain);

        when(productDomainRepository.getStockQuantity(any(ProductIdDomain.class)))
                .thenReturn(productDomain.getStock());

        cartService.deleteAllItemsFromCart(cartItemsDomain);

        verify(productDomainRepository, atLeastOnce()).updateStockQuantity(any(ProductIdDomain.class), any(Integer.class));
        verify(productDomainRepository).updateStockQuantity(productIdDomainArgCaptor.capture(), quantityArgCaptor.capture());

        var actualQuantity = quantityArgCaptor.getValue();
        var actualProductIdDomain = productIdDomainArgCaptor.getValue();
        assertEquals(expectedStockQuantity, actualQuantity);
        assertEquals(productDomain.getProductIdDomain().getId(), actualProductIdDomain.getId());

        assertTrue(cartItemsDomain.isEmpty());
    }

    @Test
    void deleteAllItemFromCartWhenProductNotFound() {
        var cartItemDomain = CartItemDomainMother.onlyOneInStocks();
        var productDomain = ProductDomainMother.fromCartItem(cartItemDomain);

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(productDomain.getProductIdDomain().getId(), cartItemDomain);

        when(productDomainRepository.getStockQuantity(any(ProductIdDomain.class)))
                .thenThrow(new RuntimeException("Error getting the stock quantity"));

        assertThrows(RuntimeException.class,
                () -> cartService.deleteAllItemsFromCart(cartItemsDomain));
        verify(productDomainRepository, never()).updateStockQuantity(any(ProductIdDomain.class), any(Integer.class));

        assertFalse(cartItemsDomain.isEmpty());
    }

    @Test
    void deleteAllItemFromCartWhenEmptyCart() {
        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();

        assertThrows(CartEmptyException.class,
                () -> cartService.deleteAllItemsFromCart(cartItemsDomain));
        verify(productDomainRepository, never()).updateStockQuantity(any(ProductIdDomain.class), any(Integer.class));
    }
}
