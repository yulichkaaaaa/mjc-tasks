package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.error.CustomErrorCode;
import com.epam.esm.error.Error;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.NotValidFieldsException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.LocaleService;
import com.epam.esm.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * Rest controller that processes requests with the gift certificate.
 *
 * @author Shuleiko Yulia
 */
@RestController
@RequestMapping("gift-certificate")
public class GiftCertificateController {

    private static final String ENTITY_NOT_FOUND_ERROR = "entity_not_found";
    private static final String ENTITY_NOT_EXIST_ERROR = "entity_already_exists";
    private static final String ENTITY_ALREADY_EXISTS_ERROR = "entity_not_exist";
    private GiftCertificateService giftCertificateService;
    private GiftCertificateValidator giftCertificateValidator;
    private LocaleService localeService;

    /**
     * Setter method of the {@code LocaleService} object.
     *
     * @param localeService the {@code LocaleService} object
     */
    @Autowired
    public void setLocaleService(LocaleService localeService) {
        this.localeService = localeService;
    }

    /**
     * Set validator to binder.
     *
     * @param binder the {@code WebDataBinder} object
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(giftCertificateValidator);
    }

    /**
     * Setter method of the {@code GiftCertificateService} object.
     *
     * @param giftCertificateService the {@code GiftCertificateService} object
     */
    @Autowired
    public void setGiftCertificateService(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Setter method of the {@code GiftCertificateValidator} object.
     *
     * @param giftCertificateValidator the {@code GiftCertificateValidator} object
     */
    @Autowired
    public void setGiftCertificateValidator(GiftCertificateValidator giftCertificateValidator) {
        this.giftCertificateValidator = giftCertificateValidator;
    }

    /**
     * Find gift certificate by it's id.
     *
     * @param id id of the gift certificate
     * @return the {@code GiftCertificateDto} object
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public GiftCertificateDto findGiftCertificateById(@PathVariable long id){
        return giftCertificateService.findGiftCertificateById(id);
    }

    /**
     * Delete the certificate by id.
     *
     * @param id id of the gift certificate
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGiftCertificateById(@PathVariable long id){
        giftCertificateService.deleteGiftCertificateById(id);
    }

    /**
     * Create new certificate.
     *
     * @param giftCertificateDto the {@code GiftCertificateDto} object
     * @param bindingResult the {@code BindingResult} object
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createGiftCertificate(@Valid GiftCertificateDto giftCertificateDto,
                                                    BindingResult bindingResult){
        if (!bindingResult.hasErrors()) {
            throw new NotValidFieldsException(bindingResult);
        }
        giftCertificateService.createGiftCertificate(giftCertificateDto);
    }

    /**
     * Update gift certificate.
     *
     * @param id id of the gift certificate
     * @param giftCertificateDto the {@code GiftCertificateDto} object
     * @param bindingResult the {@code BindingResult} object
     */
    @RequestMapping(value = "{/id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGiftCertificate(@PathVariable long id,
                                      @Valid GiftCertificateDto giftCertificateDto,
                                                    BindingResult bindingResult){
        if (!bindingResult.hasErrors()) {
            throw new NotValidFieldsException(bindingResult);
        }
        giftCertificateDto.setId(id);
        giftCertificateService.updateGiftCertificate(giftCertificateDto);
    }

    /**
     * Find certificates by criteria.
     *
     * @param tagName name of tag
     * @param giftCertificateName part of the name of the certificate
     * @param giftCertificateDescription part of the description of the certificate
     * @return list of the certificates
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<GiftCertificateDto> findCertificates(@RequestParam(required = false) String tagName,
                                                    @RequestParam(required = false) String giftCertificateName,
                                                    @RequestParam(required = false) String giftCertificateDescription) {
        GiftCertificateDto giftCertificate = new GiftCertificateDto();
        giftCertificate.setName(giftCertificateName);
        giftCertificate.setDescription(giftCertificateDescription);
        giftCertificate.addTag(new TagDto(tagName));
        return giftCertificateService.findGiftCertificatesByCriteria(giftCertificate);
    }

    /**
     * Handle the {@code EntityNotExistException}.
     *
     * @param ex the {@code EntityNotExistException} object
     * @return the {@code Error} object
     */
    @ExceptionHandler(EntityNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleGiftCertificateNotExists(EntityNotExistException ex){
        return new Error(CustomErrorCode.GIFT_CERTIFICATE_NOT_EXIST.code,
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
    public Error handleGiftCertificateNotFound(EntityNotFoundException ex){
        return new Error(CustomErrorCode.GIFT_CERTIFICATE_NOT_FOUND.code,
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
    public Error handleGiftCertificateAlreadyExists(EntityAlreadyExistsException ex){
        return new Error(CustomErrorCode.GIFT_CERTIFICATE_ALREADY_EXISTS.code,
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
    public Error handleGiftCertificateFieldsNotValid(NotValidFieldsException ex){
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errorMessage = new StringBuilder();
        for (Object object : bindingResult.getAllErrors()) {
            if(object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;
                errorMessage.append(localeService.getLocaleMessage(fieldError.toString()));
            }
        }
        return new Error(CustomErrorCode.GIFT_CERTIFICATE_FIELDS_NOT_VALID.code,
                errorMessage.toString());
    }

}
