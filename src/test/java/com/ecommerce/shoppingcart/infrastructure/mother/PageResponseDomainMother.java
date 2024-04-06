package com.ecommerce.shoppingcart.infrastructure.mother;

import com.ecommerce.shoppingcart.domain.valueobjects.PageResponseDomain;

/**
 * Generate data for Page Response Domain
 */
public class PageResponseDomainMother {
    /**
     *
     * @return a Page Response Domain
     */
    public static PageResponseDomain random() {
        return new PageResponseDomain(
            FakerMother.getFaker().random().nextLong(),
            FakerMother.getFaker().random().nextInt()
        );
    }
}
