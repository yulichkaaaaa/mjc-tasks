package com.epam.esm.controller;

import com.epam.esm.error.CustomErrorCode;
import com.epam.esm.error.Error;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.LocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    private static final String ENTITY_NOT_FOUND_ERROR = "entity_not_found";
    private static final String ENTITY_NOT_EXIST_ERROR = "entity_already_exists";
    private static final String ENTITY_ALREADY_EXISTS_ERROR = "entity_not_exist";
    private LocaleService localeService;

    @Autowired
    public void setLocaleService(LocaleService localeService) {
        this.localeService = localeService;
    }

    @ExceptionHandler(EntityNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleTagNotExists(EntityNotExistException ex){
        return new Error(CustomErrorCode.TAG_NOT_EXIST.code,
                localeService.getLocaleMessage(ENTITY_NOT_EXIST_ERROR, ex.getId()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleTagNotFound(EntityNotFoundException ex){
        return new Error(CustomErrorCode.TAG_NOT_FOUND.code,
                localeService.getLocaleMessage(ENTITY_NOT_FOUND_ERROR, ex.getId()));
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleTagAlreadyExists(EntityAlreadyExistsException ex){
        return new Error(CustomErrorCode.TAG_ALREADY_EXISTS.code,
                localeService.getLocaleMessage(ENTITY_ALREADY_EXISTS_ERROR, ex.getId()));
    }
}
