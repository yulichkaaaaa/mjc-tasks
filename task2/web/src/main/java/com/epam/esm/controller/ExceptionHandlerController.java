package com.epam.esm.controller;

import com.epam.esm.error.CustomError;
import com.epam.esm.service.LocaleService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * General exception handler.
 *
 * @author Shuleiko Yulia
 */
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    private LocaleService localeService;
    private static final String NOT_SUPPORTED_METHOD_ERROR = "request_method_not_supported";
    private static final String GENERAL_ERROR = "general_error";

    public ExceptionHandlerController(LocaleService localeService) {
        this.localeService = localeService;
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request) {
        CustomError error = new CustomError(status.value(), localeService.getLocaleMessage(NOT_SUPPORTED_METHOD_ERROR));
        return new ResponseEntity<>(error, status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        CustomError error = new CustomError(status.value(), localeService.getLocaleMessage(GENERAL_ERROR));
        return new ResponseEntity<>(error, status);
    }
}
