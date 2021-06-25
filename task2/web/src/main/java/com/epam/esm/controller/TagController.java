package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.error.CustomErrorCode;
import com.epam.esm.error.CustomError;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.NotValidFieldsException;
import com.epam.esm.service.LocaleService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.Valid;

/**
 * Rest controller that processes requests with the tags.
 *
 * @author Shuleiko Yulia
 */
@RestController
@RequestMapping("tags")
public class TagController {

    private static final String ENTITY_NOT_FOUND_ERROR = "entity_not_found";
    private static final String ENTITY_NOT_EXIST_ERROR = "entity_not_exist";
    private static final String ENTITY_ALREADY_EXISTS_ERROR = "entity_already_exists";
    private TagService tagService;
    private TagValidator tagValidator;
    private LocaleService localeService;

    public TagController(TagService tagService,
                         TagValidator tagValidator,
                         LocaleService localeService) {
        this.tagService = tagService;
        this.tagValidator = tagValidator;
        this.localeService = localeService;
    }

    /**
     * Set validator to binder.
     *
     * @param binder the {@code WebDataBinder} object
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(tagValidator);
    }

    /**
     * Delete tag.
     *
     * @param id id of the tag
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTag(@PathVariable long id) {
        tagService.deleteTag(id);
    }

    /**
     * Find tag by id.
     *
     * @param id id of the tag
     * @return the {@code TagDto} object
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TagDto findTagById(@PathVariable long id) {
        return tagService.findTagById(id);
    }

    /**
     * Create new tag.
     *
     * @param tagDto        the {@code TagDto} object
     * @param bindingResult the {@code BindingResult} object
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void createTag(@RequestBody @Valid TagDto tagDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new NotValidFieldsException(bindingResult);
        }
        tagService.createTag(tagDto);
    }

    /**
     * Handle the {@code EntityNotExistException}.
     *
     * @param ex the {@code EntityNotExistException} object
     * @return the {@code Error} object
     */
    @ExceptionHandler(EntityNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomError handleTagNotExists(EntityNotExistException ex) {
        return new CustomError(CustomErrorCode.TAG_NOT_EXIST.code,
                localeService.getLocaleMessage(ENTITY_NOT_EXIST_ERROR, ex.getId()));
    }

    /**
     * Handle the {@code EntityNotFoundException}.
     *
     * @param ex the {@code EntityNotFoundException} object
     * @return the {@code Error} object
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomError handleTagNotFound(EntityNotFoundException ex) {
        return new CustomError(CustomErrorCode.TAG_NOT_FOUND.code,
                localeService.getLocaleMessage(ENTITY_NOT_FOUND_ERROR, ex.getId()));
    }

    /**
     * Handle the {@code EntityAlreadyExistsException}.
     *
     * @param ex the {@code EntityAlreadyExistsException} object
     * @return the {@code Error} object
     */
    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomError handleTagAlreadyExists(EntityAlreadyExistsException ex) {
        return new CustomError(CustomErrorCode.TAG_ALREADY_EXISTS.code,
                localeService.getLocaleMessage(ENTITY_ALREADY_EXISTS_ERROR, ex.getId()));
    }

    /**
     * Handle the {@code NotValidFieldsException}.
     *
     * @param ex the {@code NotValidFieldsException} object
     * @return the {@code Error} object
     */
    @ExceptionHandler(NotValidFieldsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomError handleTagFieldsNotValid(NotValidFieldsException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errorMessage = new StringBuilder();
        for (Object object : bindingResult.getAllErrors()) {
            if (object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;
                errorMessage.append(localeService.getLocaleMessage(fieldError.getCode()));
            }
        }
        return new CustomError(CustomErrorCode.TAG_FIELDS_NOT_VALID.code, errorMessage.toString());
    }
}


