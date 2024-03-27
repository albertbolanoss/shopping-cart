package com.perficient.shoppingcart.infrastructure.api.exceptions;

import com.fasterxml.jackson.core.JsonParseException;

import java.util.Arrays;
import java.util.Locale;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * @author : github.com/sharmasourabh
 * @project : Chapter03 - Modern API Development with Spring and Spring Boot Ed 2
 * @created : 31/10/2022, Monday
 **/
@ControllerAdvice
public class RestApiErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(RestApiErrorHandler.class);
    private final MessageSource messageSource;

    @Autowired
    public RestApiErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(HttpServletRequest request, Exception ex,
                                                 Locale locale) {
        Error error = new Error(ErrorCode.GENERIC_ERROR.getErrMsgKey(), ErrorCode.GENERIC_ERROR.getErrCode(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURL().toString(), request.getMethod());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Error> handleHttpMediaTypeNotSupportedException(HttpServletRequest request,
                                                                          HttpMediaTypeNotSupportedException ex,
                                                                          Locale locale) {
        Error error = new Error(ErrorCode.HTTP_MEDIATYPE_NOT_SUPPORTED.getErrMsgKey(),
                ErrorCode.HTTP_MEDIATYPE_NOT_SUPPORTED.getErrCode(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), request.getRequestURL().toString(), request.getMethod());
        log.info("HttpMediaTypeNotSupportedException :: request.getMethod(): " + request.getMethod());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity<Error> handleHttpMessageNotWritableException(HttpServletRequest request,
                                                                       HttpMessageNotWritableException ex,
                                                                       Locale locale) {
        Error error = new Error(ErrorCode.HTTP_MESSAGE_NOT_WRITABLE.getErrMsgKey(),
                ErrorCode.HTTP_MESSAGE_NOT_WRITABLE.getErrCode(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), request.getRequestURL().toString(), request.getMethod());
        log.info("HttpMessageNotWritableException :: request.getMethod(): " + request.getMethod());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<Error> handleHttpMediaTypeNotAcceptableException(HttpServletRequest request,
                                                                           HttpMediaTypeNotAcceptableException ex,
                                                                           Locale locale) {
        Error error = new Error(ErrorCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE.getErrMsgKey(),
                ErrorCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE.getErrCode(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), request.getRequestURL().toString(), request.getMethod());
        log.info("HttpMediaTypeNotAcceptableException :: request.getMethod(): " + request.getMethod());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> handleHttpMessageNotReadableException(HttpServletRequest request,
                                                                       HttpMessageNotReadableException ex,
                                                                       Locale locale) {
        Error error = new Error(ErrorCode.HTTP_MESSAGE_NOT_READABLE.getErrMsgKey(),
                ErrorCode.HTTP_MESSAGE_NOT_READABLE.getErrCode(),
                HttpStatus.NOT_ACCEPTABLE.value(), request.getRequestURL().toString(), request.getMethod());
        log.info("HttpMessageNotReadableException :: request.getMethod(): " + request.getMethod());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<Error> handleJsonParseException(HttpServletRequest request,
                                                          JsonParseException ex,
                                                          Locale locale) {
        Error error = new Error(ErrorCode.JSON_PARSE_ERROR.getErrMsgKey(),
                ErrorCode.JSON_PARSE_ERROR.getErrCode(),
                HttpStatus.NOT_ACCEPTABLE.value(), request.getRequestURL().toString(), request.getMethod());
        log.info("JsonParseException :: request.getMethod(): " + request.getMethod());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            HttpClientErrorException.class
    })
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

}
