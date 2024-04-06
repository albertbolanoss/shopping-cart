package com.ecommerce.shoppingcart.infrastructure.repository;

import com.ecommerce.shoppingcart.domain.exceptions.NotAvailableInStockException;
import com.ecommerce.shoppingcart.domain.valueobjects.ProductIdDomain;
import com.ecommerce.shoppingcart.domain.repositories.ProductDomainRepository;
import com.ecommerce.shoppingcart.domain.valueobjects.CartItemDomain;
import com.ecommerce.shoppingcart.domain.valueobjects.ProductDomain;
import com.ecommerce.shoppingcart.infrastructure.entities.Product;
import com.ecommerce.shoppingcart.infrastructure.mappers.ProductDomainMapper;
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
        List<ObjectError> errors = new ArrayList<>();
        HashMap<String, Integer> quantityInStock = new HashMap<>();
        HashMap<String, Product> products = new HashMap<>();

        cartItemsDomains.forEach(cartItemDomain -> {
            var productId = cartItemDomain.getProductIdDomain().getId();
            var foundProduct = productRepository.getById(productId);
            var newQuantity = productCacheRepository.getStockQuantity(productId) - cartItemDomain.getQuantity();

            if (foundProduct.isPresent() &&  newQuantity >= 0) {
                var product = foundProduct.get();
                product.setStock(newQuantity);
                quantityInStock.put(productId, newQuantity);
                products.put(productId, product);
            } else {
                foundProduct.ifPresentOrElse(entity -> {
                    productCacheRepository.deleteStockQuantity(productId);

                    var message = String.format(
                            "Do not have enough items (%s) to ship your order.", Math.abs(newQuantity));
                        errors.add(new ObjectError(entity.getName(), message));
                    }, () -> {
                        var message = String.format("The product with id (%s) not exist.", productId);
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

            productRepository.save(products.get(productId));
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
