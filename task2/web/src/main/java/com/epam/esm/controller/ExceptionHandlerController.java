package com.epam.esm.controller;

import com.epam.esm.response.CustomResponse;
import com.epam.esm.response.CustomCode;
import com.epam.esm.exception.pagination.IncorrectPageNumberException;
import com.epam.esm.exception.pagination.IncorrectPageSizeException;
import com.epam.esm.exception.pagination.NoSuchPageException;
import com.epam.esm.exception.pagination.TooMuchPageElementsException;
import com.epam.esm.service.LocaleService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * General exception handler.
 *
 * @author Shuleiko Yulia
 */
@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    private LocaleService localeService;
    private static final String NOT_SUPPORTED_METHOD_ERROR = "request_method_not_supported";
    private static final String GENERAL_ERROR = "general_error";
    private static final String TOO_MUCH_PAGE_ELEMENTS_ERROR = "too_much_page_elements";
    private static final String INCORRECT_PAGE_SIZE_ERROR = "incorrect_page_size";
    private static final String INCORRECT_PAGE_NUMBER_ERROR = "incorrect_page_number";
    private static final String NO_SUCH_PAGE_ERROR = "no_such_page";

    /**
     * Construct controller with all necessary dependencies.
     */
    public ExceptionHandlerController(LocaleService localeService) {
        this.localeService = localeService;
    }

    /**
     * Handle the {@code TooMuchPageElementsException}.
     *
     * @param ex the {@code TooMuchPageElementsException} object
     * @return the {@code CustomError} object
     */
    @ExceptionHandler(TooMuchPageElementsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleTooMuchPageElements(TooMuchPageElementsException ex) {
        return new CustomResponse(CustomCode.TOO_MUCH_PAGE_ELEMENTS.code,
                localeService.getLocaleMessage(TOO_MUCH_PAGE_ELEMENTS_ERROR, ex.getElementsCount()));
    }

    /**
     * Handle the {@code IncorrectPageNumberException}.
     *
     * @param ex the {@code IncorrectPageNumberException} object
     * @return the {@code CustomError} object
     */
    @ExceptionHandler(IncorrectPageNumberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleIncorrectPageNumber(IncorrectPageNumberException ex) {
        return new CustomResponse(CustomCode.INCORRECT_PAGE_NUMBER.code,
                localeService.getLocaleMessage(INCORRECT_PAGE_NUMBER_ERROR, ex.getPageNumber()));
    }

    /**
     * Handle the {@code IncorrectPageSizeException}.
     *
     * @param ex the {@code IncorrectPageSizeException} object
     * @return the {@code CustomError} object
     */
    @ExceptionHandler(IncorrectPageSizeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleIncorrectPageSize(IncorrectPageSizeException ex) {
        return new CustomResponse(CustomCode.INCORRECT_PAGE_SIZE.code,
                localeService.getLocaleMessage(INCORRECT_PAGE_SIZE_ERROR, ex.getPageSize()));
    }

    /**
     * Handle the {@code NoSuchPageException}.
     *
     * @param ex the {@code NoSuchPageException} object
     * @return the {@code CustomError} object
     */
    @ExceptionHandler(NoSuchPageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleNoSuchPage(NoSuchPageException ex) {
        return new CustomResponse(CustomCode.NO_SUCH_PAGE_EXCEPTION.code,
                localeService.getLocaleMessage(NO_SUCH_PAGE_ERROR, ex.getPageNumber()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request) {
        CustomResponse error = new CustomResponse(status.value(), localeService.getLocaleMessage(NOT_SUPPORTED_METHOD_ERROR));
        return new ResponseEntity<>(error, status);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        CustomResponse error = new CustomResponse(status.value(), localeService.getLocaleMessage(GENERAL_ERROR));
        return new ResponseEntity<>(error, status);
    }
}
