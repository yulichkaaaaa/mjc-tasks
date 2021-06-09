package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.LocaleService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tag")
public class TagController {

    private TagService tagService;
    private GiftCertificateValidator giftCertificateValidator;
    private LocaleService localeService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(giftCertificateValidator);
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setGiftCertificateValidator(GiftCertificateValidator giftCertificateValidator) {
        this.giftCertificateValidator = giftCertificateValidator;
    }

    @Autowired
    public void setLocaleService(LocaleService localeService) {
        this.localeService = localeService;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping("/{id}")
    public void deleteTag(@PathVariable long id){
        tagService.deleteTag(id);
    }

    @RequestMapping("/{id}")
    public TagDto findTagById(@PathVariable long id){
        return tagService.findTagById(id);
    }

    @ExceptionHandler(EntityNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleTagNotExists(EntityNotExistException ex){
        return new Error();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleTagNotFound(EntityNotFoundException ex){
        return new Error();
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleTagAlreadyExists(EntityAlreadyExistsException ex){
        return new Error();
    }
}


