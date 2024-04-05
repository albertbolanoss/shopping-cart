package com.perficient.shoppingcart.infrastructure.api.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;

/**
 * @author : github.com/sharmasourabh
 * @project : Chapter03 - Modern API Development with Spring and Spring Boot Ed 2
 * @created : 31/10/2022, Monday
 **/
@ControllerAdvice
@Slf4j
public class RestApiErrorHandler {
    private final MessageSource messageSource;

    @Autowired
    public RestApiErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Error> handleHttpMediaTypeNotSupportedException(HttpServletRequest request) {
        var httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        Error error = new Error(
                ErrorCode.HTTP_MEDIATYPE_NOT_SUPPORTED.getErrCode(),
                ErrorCode.HTTP_MEDIATYPE_NOT_SUPPORTED.getErrMsgKey(),
                httpStatus.value(),
                request.getRequestURL().toString(),
                request.getMethod()
        );
        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity<Error> handleHttpMessageNotWritableException(HttpServletRequest request) {
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        Error error = new Error(
                ErrorCode.HTTP_MESSAGE_NOT_WRITABLE.getErrCode(),
                ErrorCode.HTTP_MESSAGE_NOT_WRITABLE.getErrMsgKey(),
                httpStatus.value(),
                request.getRequestURL().toString(),
                request.getMethod()
        );
        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<Error> handleHttpMediaTypeNotAcceptableException(HttpServletRequest request) {
        var httpStatus = HttpStatus.NOT_ACCEPTABLE;
        Error error = new Error(
                ErrorCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE.getErrCode(),
                ErrorCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE.getErrMsgKey(),
                httpStatus.value(),
                request.getRequestURL().toString(),
                request.getMethod()
        );
        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> handleHttpMessageNotReadableException(HttpServletRequest request) {
        var httpStatus = HttpStatus.BAD_REQUEST;
        Error error = new Error(
                ErrorCode.HTTP_MESSAGE_NOT_READABLE.getErrCode(),
                ErrorCode.HTTP_MESSAGE_NOT_READABLE.getErrMsgKey(),
                httpStatus.value(),
                request.getRequestURL().toString(),
                request.getMethod()
        );
        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<Error> handleJsonParseException(HttpServletRequest request) {
        var httpStatus = HttpStatus.BAD_REQUEST;
        Error error = new Error(
                ErrorCode.JSON_PARSE_ERROR.getErrCode(),
                ErrorCode.JSON_PARSE_ERROR.getErrMsgKey(),
                httpStatus.value(),
                request.getRequestURL().toString(),
                request.getMethod()
        );
        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Error> handleHttpClientErrorException(
            HttpServletRequest request, HttpClientErrorException ex) {

        var httpCode = Arrays.stream(HttpStatus.values())
                .filter(status -> status.value() == ex.getStatusCode().value())
                .findFirst()
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR);

        Error error = new Error(
                ErrorCode.APPLICATION_VALIDATION_ERROR.getErrCode(),
                ex.getMessage(),
                ex.getStatusCode().value(),
                request.getRequestURL().toString(),
                request.getMethod()
        );

        return new ResponseEntity<>(error, httpCode);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleMethodArgumentNotValidException(
            HttpServletRequest request, MethodArgumentNotValidException ex) {

        var httpCode = Arrays.stream(HttpStatus.values())
                .filter(status -> status.value() == ex.getStatusCode().value())
                .findFirst()
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR);

        Error error = new Error(
                ErrorCode.APPLICATION_VALIDATION_ERROR.getErrCode(),
                ex.getAllErrors().toString(),
                ex.getStatusCode().value(),
                request.getRequestURL().toString(),
                request.getMethod()
        );

        return new ResponseEntity<>(error, httpCode);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<Error> handleException(HttpServletRequest request, Exception ex) {
        Error error = new Error(
                ErrorCode.GENERIC_ERROR.getErrCode(),
                ErrorCode.GENERIC_ERROR.getErrMsgKey(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURL().toString(),
                request.getMethod()
        );
        log.error("Shopping Cart exception: ", ex);

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
