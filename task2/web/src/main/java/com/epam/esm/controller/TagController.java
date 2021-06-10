package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.error.Error;
import com.epam.esm.error.CustomErrorCode;
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

    private static final String TAG_NOT_FOUND_ERROR = "tag_not_found";
    private static final String TAG_NOT_EXIST_ERROR = "tag_already_exists";
    private static final String TAG_ALREADY_EXISTS_ERROR = "tag_not_exist";
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
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTag(@PathVariable long id){
        tagService.deleteTag(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TagDto findTagById(@PathVariable long id){
        return tagService.findTagById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void createTag(TagDto tagDto){
        tagService.createTag(tagDto);
    }

    @ExceptionHandler(EntityNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleTagNotExists(EntityNotExistException ex){
        return new Error(CustomErrorCode.TAG_NOT_EXIST.code,
                localeService.getLocaleMessage(TAG_NOT_EXIST_ERROR, ex.getId()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleTagNotFound(EntityNotFoundException ex){
        return new Error(CustomErrorCode.TAG_NOT_FOUND.code,
                localeService.getLocaleMessage(TAG_NOT_FOUND_ERROR, ex.getId()));
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleTagAlreadyExists(EntityAlreadyExistsException ex){
        return new Error(CustomErrorCode.TAG_ALREADY_EXISTS.code,
                localeService.getLocaleMessage(TAG_ALREADY_EXISTS_ERROR, ex.getId()));
    }
}


