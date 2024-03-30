package com.perficient.shoppingcart.infrastructure.repository;

import com.perficient.shoppingcart.domain.repositories.ProductDomainRepository;
import com.perficient.shoppingcart.domain.valueobjects.ProductDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import com.perficient.shoppingcart.infrastructure.mappers.ProductDomainMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.UUID;


/**
 * Product domain repository
 */
@Repository
@Slf4j
public class ProductDomainRepositoryImpl implements ProductDomainRepository {
    /**
     * The product repository
     */
    private final ProductRepository productRepository;

    @Autowired
    public ProductDomainRepositoryImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Cacheable(value="ProductInStock", key="#productIdDomain.id")
    public ProductDomain getProductFromStock(ProductIdDomain productIdDomain) {
        return productRepository.findById(UUID.fromString(productIdDomain.getId()))
                .map(ProductDomainMapper::fromEntity)
                .orElse(null);
    }

    @Override
    @CachePut(value="ProductInStock", key="#productIdDomain.id")
    public void updateProductInStock(ProductDomain productDomain) {
        log.debug("Update product: {} - with stock: {}",
                productDomain.getCode(), productDomain.getStock());
    }

}
