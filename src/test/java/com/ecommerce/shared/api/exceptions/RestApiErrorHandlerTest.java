package com.ecommerce.shared.api.exceptions;

import com.ecommerce.shared.api.exceptions.ErrorCode;
import com.ecommerce.shared.api.exceptions.RestApiErrorHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class RestApiErrorHandlerTest  {
    @InjectMocks
    private RestApiErrorHandler restApiErrorHandler;

    @Mock
    private HttpServletRequest httpServletRequest;

    private final String RequestURL = "http://host/api/v1/myResource";
    @Test
    void handleHttpMediaTypeNotSupportedException() {
        var HTTPMethod = "GET";
        when(httpServletRequest.getMethod()).thenReturn(HTTPMethod);
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(RequestURL));

        var response = restApiErrorHandler.handleHttpMediaTypeNotSupportedException(httpServletRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals(ErrorCode.HTTP_MEDIATYPE_NOT_SUPPORTED.getErrCode(), response.getBody().getErrorCode());
        assertEquals(ErrorCode.HTTP_MEDIATYPE_NOT_SUPPORTED.getErrMsgKey(), response.getBody().getMessage());
        assertEquals(RequestURL, response.getBody().getUrl());
        assertEquals(HTTPMethod, response.getBody().getReqMethod());
    }

    @Test
    void handleHttpMessageNotWritableException() {
        var HTTPMethod = "GET";
        when(httpServletRequest.getMethod()).thenReturn(HTTPMethod);
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(RequestURL));

        var response = restApiErrorHandler.handleHttpMessageNotWritableException(httpServletRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals(ErrorCode.HTTP_MESSAGE_NOT_WRITABLE.getErrCode(), response.getBody().getErrorCode());
        assertEquals(ErrorCode.HTTP_MESSAGE_NOT_WRITABLE.getErrMsgKey(), response.getBody().getMessage());
        assertEquals(RequestURL, response.getBody().getUrl());
        assertEquals(HTTPMethod, response.getBody().getReqMethod());
    }

    @Test
    void handleHttpMediaTypeNotAcceptableException() {
        var HTTPMethod = "GET";
        when(httpServletRequest.getMethod()).thenReturn(HTTPMethod);
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(RequestURL));

        var response = restApiErrorHandler.handleHttpMediaTypeNotAcceptableException(httpServletRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals(ErrorCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE.getErrCode(), response.getBody().getErrorCode());
        assertEquals(ErrorCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE.getErrMsgKey(), response.getBody().getMessage());
        assertEquals(RequestURL, response.getBody().getUrl());
        assertEquals(HTTPMethod, response.getBody().getReqMethod());
    }

    @Test
    void handleHttpMessageNotReadableException() {
        var HTTPMethod = "GET";
        when(httpServletRequest.getMethod()).thenReturn(HTTPMethod);
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(RequestURL));

        var response = restApiErrorHandler.handleHttpMessageNotReadableException(httpServletRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals(ErrorCode.HTTP_MESSAGE_NOT_READABLE.getErrCode(), response.getBody().getErrorCode());
        assertEquals(ErrorCode.HTTP_MESSAGE_NOT_READABLE.getErrMsgKey(), response.getBody().getMessage());
        assertEquals(RequestURL, response.getBody().getUrl());
        assertEquals(HTTPMethod, response.getBody().getReqMethod());
    }

    @Test
    void handleJsonParseException() {
        var HTTPMethod = "GET";
        when(httpServletRequest.getMethod()).thenReturn(HTTPMethod);
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(RequestURL));

        var response = restApiErrorHandler.handleJsonParseException(httpServletRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals(ErrorCode.JSON_PARSE_ERROR.getErrCode(), response.getBody().getErrorCode());
        assertEquals(ErrorCode.JSON_PARSE_ERROR.getErrMsgKey(), response.getBody().getMessage());
        assertEquals(RequestURL, response.getBody().getUrl());
        assertEquals(HTTPMethod, response.getBody().getReqMethod());
    }

    @Test
    void handleHttpClientErrorException() {
        var HTTPMethod = "GET";
        var HttpClientErrorException = new HttpClientErrorException(HttpStatus.NOT_FOUND, "Object not found");

        when(httpServletRequest.getMethod()).thenReturn(HTTPMethod);
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(RequestURL));

        var response = restApiErrorHandler.handleHttpClientErrorException(httpServletRequest, HttpClientErrorException);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals(ErrorCode.APPLICATION_VALIDATION_ERROR.getErrCode(), response.getBody().getErrorCode());
        assertEquals(HttpClientErrorException.getMessage(), response.getBody().getMessage());
        assertEquals(RequestURL, response.getBody().getUrl());
        assertEquals(HTTPMethod, response.getBody().getReqMethod());
    }

    @Test
    void handleMethodArgumentNotValidException() {
        var HTTPMethod = "GET";
        var objectError = new ObjectError("field", "mensaje de error");
        var allErrors = List.of(objectError);


        MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);

        when(httpServletRequest.getMethod()).thenReturn(HTTPMethod);
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(RequestURL));
        when(methodArgumentNotValidException.getStatusCode()).thenReturn(HttpStatus.CONFLICT);
        when(methodArgumentNotValidException.getAllErrors()).thenReturn(allErrors);

        var response = restApiErrorHandler.handleMethodArgumentNotValidException(httpServletRequest, methodArgumentNotValidException);

        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals(ErrorCode.APPLICATION_VALIDATION_ERROR.getErrCode(), response.getBody().getErrorCode());
        assertEquals(allErrors.toString(), response.getBody().getMessage());
        assertEquals(RequestURL, response.getBody().getUrl());
        assertEquals(HTTPMethod, response.getBody().getReqMethod());
    }

    @Test
    void handleException() {
        var HTTPMethod = "GET";
        var runtimeException = new RuntimeException("runtime exception");

        when(httpServletRequest.getMethod()).thenReturn(HTTPMethod);
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(RequestURL));

        var response = restApiErrorHandler.handleException(httpServletRequest, runtimeException);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals(ErrorCode.GENERIC_ERROR.getErrCode(), response.getBody().getErrorCode());
        assertEquals(ErrorCode.GENERIC_ERROR.getErrMsgKey(), response.getBody().getMessage());
        assertEquals(RequestURL, response.getBody().getUrl());
        assertEquals(HTTPMethod, response.getBody().getReqMethod());
    }


}
