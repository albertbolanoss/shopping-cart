package com.perficient.shoppingcart.application.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Error
 * <p>
 * Complex type that contains error details for a REST API calls.
 *
 * @author : github.com/sharmasourabh
 * @project : Chapter03 - Modern API Development with Spring and Spring Boot Ed 2
 * @created : 31/10/2022, Monday
 **/

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class Error {

    private static final long serialVersionUID = 1L;
    /**
     * Application error code, which is different from HTTP error code.
     */
    private final String errorCode;

    /**
     * Short, human-readable summary of the problem.
     */
    private final String message;

    /**
     * HTTP status code for this occurrence of the problem, set by the origin server.
     */
    private final Integer status;

    /**
     * Url of request that produced the error.
     */
    private String url = "Not available";

    /**
     * Method of request that produced the error.
     */
    private String reqMethod = "Not available";
}
