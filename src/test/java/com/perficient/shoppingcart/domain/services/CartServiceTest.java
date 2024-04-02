package com.perficient.shoppingcart.domain.services;

import com.perficient.shoppingcart.domain.exceptions.CartEmptyException;
import com.perficient.shoppingcart.domain.exceptions.NotExistException;
import com.perficient.shoppingcart.domain.exceptions.ProductNotAvailableException;
import com.perficient.shoppingcart.domain.repositories.ProductDomainRepository;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import com.perficient.shoppingcart.infrastructure.mother.CartItemDomainMother;
import com.perficient.shoppingcart.infrastructure.mother.ProductDomainMother;
import com.perficient.shoppingcart.infrastructure.mother.ProductIdDomainMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CartServiceTest {
    private CartService cartService;

    @Mock
    private ProductDomainRepository productDomainRepository;

    @Mock
    private CartPaymentService cartPaymentService;

    @BeforeEach
    void init() {
        cartService = new CartService(productDomainRepository, cartPaymentService);
    }

    @Test
    void getItemFromStockSuccessfully() {
        var cartItemDomain = CartItemDomainMother.random();
        var productDomain = ProductDomainMother.random();
        var productIdDomain = new ProductIdDomain(productDomain.getProductIdDomain().getId());
        var expectedQuantity = cartItemDomain.getQuantity() + 1;

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(productDomain.getProductIdDomain().getId(), cartItemDomain);

        when(productDomainRepository.getProductFromStock(any(ProductIdDomain.class)))
                .thenReturn(Optional.of(productDomain));

        doNothing().when(productDomainRepository).updateProductInStock(any(ProductDomain.class));

        var actualCartItemDomain = cartService.getItemFromStock(productIdDomain, cartItemsDomain);

        assertNotNull(actualCartItemDomain);
        assertEquals(productDomain.getUnitPrice(), actualCartItemDomain.getUnitPrice());
        assertEquals(expectedQuantity, actualCartItemDomain.getQuantity());

    }

    @Test
    void getItemFromStockProductNotExist() {
        var productIdDomain = ProductIdDomainMother.random();
        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();

        when(productDomainRepository.getProductFromStock(any(ProductIdDomain.class)))
                .thenReturn(Optional.empty());

        assertThrows(NotExistException.class, () -> cartService.getItemFromStock(productIdDomain, cartItemsDomain));
    }

    @Test
    void getItemFromStockProductNotAvailable() {
        var productIdDomain = ProductIdDomainMother.random();
        var productDomain = ProductDomainMother.noStockAvailable();
        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();

        when(productDomainRepository.getProductFromStock(any(ProductIdDomain.class)))
                .thenReturn(Optional.of(productDomain));

        assertThrows(ProductNotAvailableException.class,
                () -> cartService.getItemFromStock(productIdDomain, cartItemsDomain));
    }

    @Test
    void deleteItemFromCart() {
        var cartItemDomain = CartItemDomainMother.twoOrMoreInStock();
        var productDomain = ProductDomainMother.random();
        var productIdDomain = new ProductIdDomain(productDomain.getProductIdDomain().getId());
        var expectedQuantity = cartItemDomain.getQuantity() - 1;

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(productDomain.getProductIdDomain().getId(), cartItemDomain);

        when(productDomainRepository.getProductFromStock(any(ProductIdDomain.class)))
                .thenReturn(Optional.of(productDomain));

        doNothing().when(productDomainRepository).updateProductInStock(any(ProductDomain.class));

        var actualCart = cartService.deleteItemFromCart(productIdDomain, cartItemsDomain);

        assertNotNull(actualCart);

        var actualCartItemDomain = actualCart.get(productIdDomain.getId());
        assertNotNull(actualCartItemDomain);
        assertEquals(cartItemDomain.getUnitPrice(), actualCartItemDomain.getUnitPrice());
        assertEquals(expectedQuantity, actualCartItemDomain.getQuantity());
    }

    @Test
    void deleteItemFromCartRemoveProduct() {
        var cartItemDomain = CartItemDomainMother.onInStocks();
        var productDomain = ProductDomainMother.random();
        var productIdDomain = new ProductIdDomain(productDomain.getProductIdDomain().getId());

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(productDomain.getProductIdDomain().getId(), cartItemDomain);

        when(productDomainRepository.getProductFromStock(any(ProductIdDomain.class)))
                .thenReturn(Optional.of(productDomain));

        doNothing().when(productDomainRepository).updateProductInStock(any(ProductDomain.class));

        var actualCart = cartService.deleteItemFromCart(productIdDomain, cartItemsDomain);

        assertNotNull(actualCart);
        assertTrue(actualCart.isEmpty());
    }

    @Test
    void deleteItemFromCartProductNotFound() {
        var cartItemDomain = CartItemDomainMother.onInStocks();
        var productDomain = ProductDomainMother.random();
        var productIdDomain = new ProductIdDomain(productDomain.getProductIdDomain().getId());

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(productDomain.getProductIdDomain().getId(), cartItemDomain);

        when(productDomainRepository.getProductFromStock(any(ProductIdDomain.class)))
                .thenThrow(new NotExistException("The product not exist"));

        assertThrows(NotExistException.class,
                () -> cartService.deleteItemFromCart(productIdDomain, cartItemsDomain));

    }

    @Test
    void deleteItemFromCartEmptyCart() {
        var productDomain = ProductDomainMother.random();
        var productIdDomain = new ProductIdDomain(productDomain.getProductIdDomain().getId());

        assertThrows(CartEmptyException.class,
                () -> cartService.deleteItemFromCart(productIdDomain, null));

    }

    @Test
    void deleteAllItemFromCart() {
        var cartItemDomain = CartItemDomainMother.twoOrMoreInStock();
        var productDomain = ProductDomainMother.random();

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(productDomain.getProductIdDomain().getId(), cartItemDomain);

        when(productDomainRepository.getProductFromStock(any(ProductIdDomain.class)))
                .thenReturn(Optional.of(productDomain));

        cartService.deleteAllItemFromCart(cartItemsDomain);

        verify(productDomainRepository, times(1)).updateProductInStock(any(ProductDomain.class));
    }

    @Test
    void deleteAllItemFromCartEmptyCart() {
        var productDomain = ProductDomainMother.random();

        when(productDomainRepository.getProductFromStock(any(ProductIdDomain.class)))
                .thenReturn(Optional.of(productDomain));

        assertThrows(CartEmptyException.class, () ->  cartService.deleteAllItemFromCart(null));
    }

    @Test
    void deleteAllItemFromCartNullableCart() {
        var productDomain = ProductDomainMother.random();

        when(productDomainRepository.getProductFromStock(any(ProductIdDomain.class)))
                .thenReturn(Optional.of(productDomain));


        assertThrows(CartEmptyException.class, () ->  cartService.deleteAllItemFromCart(null));
    }
}
