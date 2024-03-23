package com.perficient.shoppingcart.infrastructure.api.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * Generic error exception
 */
@Setter
@Getter
public class Error {
    private static final long serialVersionUID = 1L;
    private String errorCode;
    private String message;
    private Integer status;
    private String url = "Not available";
    private String reqMethod = "Not available";
}
