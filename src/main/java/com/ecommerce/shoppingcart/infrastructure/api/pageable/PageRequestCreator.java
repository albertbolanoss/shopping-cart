package com.ecommerce.shoppingcart.infrastructure.api.pageable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Create a Pageable
 */
public class PageRequestCreator {
    private static final String DESC = "desc";

    /**
     * Create a pageable request
     * @param pageNumber the page number
     * @param pageSize the page size
     * @param sortFieldsWithDirection the sort field with direction
     * @return a pageable request
     */
    public static PageRequest create(int pageNumber, int pageSize, List<String> sortFieldsWithDirection) {
        var safeSortFieldsWithDirection = Optional.ofNullable(sortFieldsWithDirection)
                .orElse(Collections.emptyList());

        List<Sort.Order> orders = safeSortFieldsWithDirection.stream()
                .map(param -> {
                    String[] parts = param.split("_");
                    String field = parts[0];
                    String direction = parts[1];
                    return direction.equalsIgnoreCase(DESC)
                            ? Sort.Order.desc(field) : Sort.Order.asc(field);
                })
                .collect(Collectors.toList());

        Sort sort = Sort.by(orders);

        return PageRequest.of(pageNumber, pageSize, sort);
    }
}
