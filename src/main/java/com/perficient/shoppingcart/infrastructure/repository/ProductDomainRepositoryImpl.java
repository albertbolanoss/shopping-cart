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

    @Autowired
    public ProductDomainRepositoryImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<ProductDomain> getProductFromStock(ProductIdDomain productIdDomain) {
        return productRepository.findById(productIdDomain.getId())
                .map(ProductDomainMapper::fromEntity);
    }
}
