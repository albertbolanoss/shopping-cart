package com.perficient.shoppingcart.domain.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PageRequestDomain {
    private int pageNumber;
    private int pageSize;
    private List<String> sort;
}
