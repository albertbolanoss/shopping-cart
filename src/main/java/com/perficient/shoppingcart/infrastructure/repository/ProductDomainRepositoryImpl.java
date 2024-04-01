package com.perficient.shoppingcart.infrastructure.repository;

import com.perficient.shoppingcart.domain.repositories.ProductDomainRepository;
import com.perficient.shoppingcart.domain.valueobjects.ProductDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import com.perficient.shoppingcart.infrastructure.mappers.ProductDomainMapper;
import com.perficient.shoppingcart.infrastructure.mappers.ProductMapper;
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
     * The product cache repository
     */
    private final ProductCacheRepository productCacheRepository;

    @Autowired
    public ProductDomainRepositoryImpl(ProductCacheRepository productCacheRepository) {
        this.productCacheRepository = productCacheRepository;
    }

    @Override
    public Optional<ProductDomain> getProductFromStock(ProductIdDomain productIdDomain) {
        return productCacheRepository.findByIdFromCache(productIdDomain.getId())
                .map(ProductDomainMapper::fromEntity);
    }

    @Override
    public void updateProductInStock(ProductDomain productDomain) {
        var product = Optional.ofNullable(productDomain)
                .map(ProductMapper::fromDomain)
                .orElse(null);

        productCacheRepository.updateProductInCache(product);
    }
}
