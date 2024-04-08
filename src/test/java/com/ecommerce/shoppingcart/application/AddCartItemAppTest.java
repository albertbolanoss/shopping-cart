package com.ecommerce.shoppingcart.application;

import com.ecommerce.shoppingcart.domain.exceptions.NotAvailableInStockException;
import com.ecommerce.shoppingcart.domain.repositories.ProductDomainRepository;
import com.ecommerce.shoppingcart.domain.valueobjects.CartItemDomain;
import com.ecommerce.shoppingcart.domain.valueobjects.ProductIdDomain;
import com.ecommerce.shoppingcart.infrastructure.mother.CartItemDomainMother;
import com.ecommerce.shoppingcart.infrastructure.mother.ProductDomainMother;
import com.ecommerce.shoppingcart.infrastructure.mother.ProductIdDomainMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddCartItemAppTest {
    private AddCartItemApp addCartItemService;

    @Mock
    private ProductDomainRepository productDomainRepository;

    @BeforeEach
    void init() {
        addCartItemService = new AddCartItemApp(productDomainRepository);
    }

    @Test
    void addItemWhenItsNewProduct() {
        var productDomain = ProductDomainMother.random();
        var productIdDomain = ProductIdDomainMother.random();
        var cartItemsDomain = List.of(
                CartItemDomainMother.random(),
                CartItemDomainMother.random(),
                CartItemDomainMother.random()
        );

        when(productDomainRepository.getProductById(any(ProductIdDomain.class))).thenReturn(Optional.of(productDomain));
        when(productDomainRepository.getStockQuantity(any(ProductIdDomain.class))).thenReturn(200);

        var newItem = addCartItemService.addItem(productIdDomain, cartItemsDomain);

        assertNotNull(newItem);
        assertEquals(1, newItem.getQuantity());
        assertEquals(productDomain.getUnitPrice(), newItem.getUnitPrice());
        assertEquals(productDomain.getProductIdDomain().getId(), newItem.getProductIdDomain().getId());
    }

    @Test
    void addItemWhenAlreadyExistInCart() {
        var cartItemDomain = CartItemDomainMother.random();
        var productIdDomain = cartItemDomain.getProductIdDomain();
        var productDomain = ProductDomainMother.fromCartItem(cartItemDomain);
        var cartItemsDomain = List.of(CartItemDomainMother.random(), CartItemDomainMother.random(), cartItemDomain);
        var expectedItemsQuantity = cartItemDomain.getQuantity() + 1;

        when(productDomainRepository.getProductById(any(ProductIdDomain.class))).thenReturn(Optional.of(productDomain));
        when(productDomainRepository.getStockQuantity(any(ProductIdDomain.class))).thenReturn(200);

        var newItem = addCartItemService.addItem(productIdDomain, cartItemsDomain);

        assertNotNull(newItem);
        assertEquals(expectedItemsQuantity, newItem.getQuantity());
        assertEquals(cartItemDomain.getUnitPrice(), newItem.getUnitPrice());
        assertEquals(cartItemDomain.getProductIdDomain().getId(), newItem.getProductIdDomain().getId());
    }

    @Test
    void addItemWhenCartIsEmpty() {
        var productDomain = ProductDomainMother.random();
        var productIdDomain = productDomain.getProductIdDomain();
        var expectedItemsQuantity =  1;
        List<CartItemDomain> cartItemsDomain = new ArrayList<>();

        when(productDomainRepository.getProductById(any(ProductIdDomain.class))).thenReturn(Optional.of(productDomain));
        when(productDomainRepository.getStockQuantity(any(ProductIdDomain.class))).thenReturn(200);

        var newItem = addCartItemService.addItem(productIdDomain, cartItemsDomain);

        assertNotNull(newItem);
        assertEquals(expectedItemsQuantity, newItem.getQuantity());
        assertEquals(productDomain.getUnitPrice(), newItem.getUnitPrice());
        assertEquals(productDomain.getProductIdDomain().getId(), newItem.getProductIdDomain().getId());
    }

    @Test
    void addItemWhenProductNotExist() {
        var productIdDomain = ProductIdDomainMother.random();
        var cartItemsDomain = List.of(CartItemDomainMother.random(),
                CartItemDomainMother.random(),
                CartItemDomainMother.random());

        when(productDomainRepository.getProductById(any(ProductIdDomain.class))).thenReturn(Optional.empty());

        assertThrows(HttpClientErrorException.class, () -> addCartItemService.addItem(productIdDomain, cartItemsDomain));
    }

    @Test
    void addItemWhenNotAvailableInStock() {
        var productDomain = ProductDomainMother.random();
        var productIdDomain = ProductIdDomainMother.random();
        var cartItemsDomain = List.of(
                CartItemDomainMother.random(),
                CartItemDomainMother.random(),
                CartItemDomainMother.random()
        );

        when(productDomainRepository.getProductById(any(ProductIdDomain.class))).thenReturn(Optional.of(productDomain));
        when(productDomainRepository.getStockQuantity(any(ProductIdDomain.class))).thenReturn(0);


        assertThrows(NotAvailableInStockException.class, () -> addCartItemService.addItem(productIdDomain, cartItemsDomain));
    }


}
