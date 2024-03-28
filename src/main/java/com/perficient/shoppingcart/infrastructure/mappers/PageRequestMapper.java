package com.perficient.shoppingcart.infrastructure.mappers;

import com.perficient.shoppingcart.domain.valueobjects.PageRequestDomain;
import com.perficient.shoppingcart.infrastructure.api.pageable.PageRequestCreator;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public class PageRequestMapper {
    public static PageRequest fromDomain(PageRequestDomain pageRequestDomain) {
        return Optional.ofNullable(pageRequestDomain)
                .map(domain -> PageRequestCreator.create(
                        domain.getPageNumber(),
                        domain.getPageSize(),
                        domain.getSort()))
                .orElse(null);
    }
}
