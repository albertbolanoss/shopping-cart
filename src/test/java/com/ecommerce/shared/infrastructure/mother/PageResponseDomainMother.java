package com.ecommerce.shared.infrastructure.mother;

import com.ecommerce.shared.domain.valueobjects.PageResponseDomain;
import com.ecommerce.shared.infrastructure.mother.FakerMother;

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
