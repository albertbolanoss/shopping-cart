package com.perficient.shoppingcart.infrastructure.repository;

import com.perficient.shoppingcart.domain.exceptions.NotAvailableInStockException;
import com.perficient.shoppingcart.domain.repositories.ProductDomainRepository;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import com.perficient.shoppingcart.infrastructure.mappers.ProductDomainMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * Product domain repository
 */
@Service
@Slf4j
public class ProductDomainRepositoryImpl implements ProductDomainRepository {
    /**
     * The product repository
     */
    private final ProductRepository productRepository;

    /**
     * The product cache repository
     */
    private final ProductCacheRepository productCacheRepository;

    @Autowired
    public ProductDomainRepositoryImpl(ProductRepository productRepository,
                                       ProductCacheRepository productCacheRepository) {
        this.productRepository = productRepository;
        this.productCacheRepository = productCacheRepository;
    }

    @Override
    public Optional<ProductDomain> getProductById(ProductIdDomain productIdDomain) {
        return productRepository.getById(productIdDomain.getId())
                .map(ProductDomainMapper::fromEntity);
    }

    @Override
    public synchronized void updateStockQuantity(List<CartItemDomain> cartItemsDomains) {
        HashMap<String, Integer> quantityInStock = new HashMap<>();
        List<ObjectError> errors = new ArrayList<>();

        cartItemsDomains.forEach(cartItemDomain -> {
            var productId = cartItemDomain.getProductIdDomain().getId();
            var newQuantity = productCacheRepository.getStockQuantity(productId) - cartItemDomain.getQuantity();

            if (newQuantity >= 0) {
                quantityInStock.put(productId, newQuantity);
            } else {
                productRepository.getById(productId)
                    .ifPresentOrElse(product -> {
                        var message = String.format(
                                "Do not have enough items (%s) to ship your order.", Math.abs(newQuantity));
                        errors.add(new ObjectError(product.getName(), message));
                    }, () -> {
                        var message = String.format(
                                "The product with id (%s) not exist.", Math.abs(newQuantity));
                        errors.add(new ObjectError(productId, message));
                    });
            }
        });

        if (!errors.isEmpty()) {
            throw new NotAvailableInStockException("Not available in stock", errors);
        }

        for (Map.Entry<String, Integer> entry : quantityInStock.entrySet()) {
            String productId = entry.getKey();
            Integer quantity = entry.getValue();
            productCacheRepository.updateStockQuantity(productId, quantity);
        }
    }

    /**
     * Get the stock availability
     * @param productIdDomain the product id
     * @return a integer
     */
    @Override
    public Integer getStockQuantity(ProductIdDomain productIdDomain) {
        return productCacheRepository.getStockQuantity(productIdDomain.getId());
    }
}
