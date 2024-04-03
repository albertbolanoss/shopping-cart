package com.perficient.shoppingcart.infrastructure.repository;

import com.perficient.shoppingcart.domain.repositories.ProductDomainRepository;
import com.perficient.shoppingcart.domain.valueobjects.ProductDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import com.perficient.shoppingcart.infrastructure.mappers.ProductDomainMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void updateStockQuantity(ProductIdDomain productIdDomain, Integer quantity) {
        productCacheRepository.updateStockQuantity(productIdDomain.getId(), quantity);
    }

    @Override
    public Integer getStockQuantity(ProductIdDomain productIdDomain) {
        return productCacheRepository.getStockQuantity(productIdDomain.getId());
    }
}
