package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.response.CustomCode;
import com.epam.esm.response.CustomResponse;
import com.epam.esm.exception.EntityNotExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.NotValidFieldsException;
import com.epam.esm.exception.pagination.NoSuchPageException;
import com.epam.esm.model.GiftCertificateModel;
import com.epam.esm.model.assembler.GiftCertificateModelAssembler;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.LocaleService;
import com.epam.esm.validator.GiftCertificateValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Rest controller that processes requests with the gift certificate.
 *
 * @author Shuleiko Yulia
 */
@RestController
@RequestMapping("/gift-certificates")
public class GiftCertificateController {

    private static final String ENTITY_NOT_FOUND_ERROR = "entity_not_found";
    private static final String ENTITY_NOT_EXIST_ERROR = "entity_not_exist";
    private static final String GIFT_CERTIFICATE_WAS_CREATED_MESSAGE = "gift_certificate_was_created";
    private static final String GIFT_CERTIFICATE_WAS_UPDATED_MESSAGE = "gift_certificate_was_updated";
    private static final String GIFT_CERTIFICATE_WAS_DELETED_MESSAGE = "gift_certificate_was_deleted";
    private GiftCertificateService giftCertificateService;
    private GiftCertificateValidator giftCertificateValidator;
    private LocaleService localeService;
    private GiftCertificateModelAssembler giftCertificateModelAssembler;
    private PagedResourcesAssembler<GiftCertificateDto> pagedGiftCertificateResourcesAssembler;

    /**
     * Construct controller with all necessary dependencies.
     */
    public GiftCertificateController(GiftCertificateService giftCertificateService,
                                     GiftCertificateValidator giftCertificateValidator,
                                     LocaleService localeService,
                                     GiftCertificateModelAssembler giftCertificateModelAssembler,
                                     PagedResourcesAssembler<GiftCertificateDto> pagedGiftCertificateResourcesAssembler) {
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateValidator = giftCertificateValidator;
        this.localeService = localeService;
        this.giftCertificateModelAssembler = giftCertificateModelAssembler;
        this.pagedGiftCertificateResourcesAssembler = pagedGiftCertificateResourcesAssembler;
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
     * Find gift certificate by it's id.
     *
     * @param id id of the gift certificate
     * @return the {@code GiftCertificateDto} object
     */
    @GetMapping(value = "/{id}")
    public GiftCertificateModel findGiftCertificateById(@PathVariable long id) {
        return giftCertificateModelAssembler.toModel(giftCertificateService.findGiftCertificateById(id));
    }

    /**
     * Delete the certificate by id.
     *
     * @param id id of the gift certificate
     */
    @DeleteMapping(value = "/{id}")
    public CustomResponse deleteGiftCertificateById(@PathVariable long id) {
        giftCertificateService.deleteGiftCertificateById(id);
        return new CustomResponse(CustomCode.GIFT_CERTIFICATE_WAS_DELETED.code,
                localeService.getLocaleMessage(GIFT_CERTIFICATE_WAS_DELETED_MESSAGE, id));
    }

    /**
     * Create new certificate.
     *
     * @param giftCertificateDto the {@code GiftCertificateDto} object
     * @param bindingResult      the {@code BindingResult} object
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomResponse createGiftCertificate(@RequestBody @Valid GiftCertificateDto giftCertificateDto,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new NotValidFieldsException(bindingResult);
        }
        long id = giftCertificateService.createGiftCertificate(giftCertificateDto);
        return new CustomResponse(CustomCode.GIFT_CERTIFICATE_WAS_CREATED.code,
                localeService.getLocaleMessage(GIFT_CERTIFICATE_WAS_CREATED_MESSAGE, id));
    }

    /**
     * Update gift certificate.
     *
     * @param giftCertificateDto the {@code GiftCertificateDto} object
     * @param bindingResult      the {@code BindingResult} object
     */
    @PatchMapping(value = "/{id}")
    public CustomResponse updateGiftCertificate(@PathVariable long id,
                                      @RequestBody @Valid GiftCertificateDto giftCertificateDto,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new NotValidFieldsException(bindingResult);
        }
        giftCertificateDto.setId(id);
        giftCertificateService.updateGiftCertificate(giftCertificateDto);
        return new CustomResponse(CustomCode.GIFT_CERTIFICATE_WAS_UPDATED.code,
                localeService.getLocaleMessage(GIFT_CERTIFICATE_WAS_UPDATED_MESSAGE, id));
    }

    /**
     * Find certificates by criteria.
     *
     * @param tagNames                   names of tag
     * @param giftCertificateName        part of the name of the certificate
     * @param giftCertificateDescription part of the description of the certificate
     * @return list of the certificates
     */
    @GetMapping
    public PagedModel<GiftCertificateModel> findCertificates(@RequestParam(required = false) String tagNames,
                                                             @RequestParam(required = false) String giftCertificateName,
                                                             @RequestParam(required = false) String giftCertificateDescription,
                                                             @RequestParam(defaultValue = "") String sortCriteria,
                                                             @RequestParam(defaultValue = "asc") String sortDirection,
                                                             @RequestParam int page,
                                                             @RequestParam int size) {
        GiftCertificateDto giftCertificate = defineSearchCriteria(giftCertificateName, giftCertificateDescription, tagNames);
        List<String> sortCriteriaList = Arrays
                .stream(sortCriteria.split(","))
                .collect(Collectors.toList());
        Pageable pageable = PageRequest.of(page, size);
        Page<GiftCertificateDto> giftCertificatesPage = giftCertificateService
                .findGiftCertificatesByCriteria(giftCertificate, sortCriteriaList, sortDirection, pageable);
        if (giftCertificatesPage.getNumber() >= giftCertificatesPage.getTotalPages()) {
            throw new NoSuchPageException(giftCertificatesPage.getNumber());
        }
        return pagedGiftCertificateResourcesAssembler.toModel(giftCertificatesPage, giftCertificateModelAssembler);
    }

    /**
     * Define criteria of the search.
     *
     * @param giftCertificateName        name of the gift certificate
     * @param giftCertificateDescription description of the gift certificate
     * @param tagNames                   names of tags
     * @return the {@code GiftCertificateDto} that represents search criteria
     */
    private GiftCertificateDto defineSearchCriteria(String giftCertificateName,
                                                    String giftCertificateDescription,
                                                    String tagNames) {
        GiftCertificateDto giftCertificate = new GiftCertificateDto();
        giftCertificate.setName(giftCertificateName);
        giftCertificate.setDescription(giftCertificateDescription);
        if (Objects.nonNull(tagNames)) {
            Arrays.stream(tagNames.split(","))
                    .map(TagDto::new)
                    .forEach(giftCertificate::addTag);
        }
        return giftCertificate;
    }

    /**
     * Handle the {@code EntityNotExistException}.
     *
     * @param ex the {@code EntityNotExistException} object
     * @return the {@code CustomError} object
     */
    @ExceptionHandler(EntityNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleGiftCertificateNotExists(EntityNotExistException ex) {
        return new CustomResponse(CustomCode.GIFT_CERTIFICATE_NOT_EXIST.code,
                localeService.getLocaleMessage(ENTITY_NOT_EXIST_ERROR, ex.getId()));
    }

    /**
     * Handle the {@code EntityNotFoundException}.
     *
     * @param ex the {@code EntityNotFoundException} object
     * @return the {@code CustomError} object
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomResponse handleGiftCertificateNotFound(EntityNotFoundException ex) {
        return new CustomResponse(CustomCode.GIFT_CERTIFICATE_NOT_FOUND.code,
                localeService.getLocaleMessage(ENTITY_NOT_FOUND_ERROR, ex.getId()));
    }

    /**
     * Handle the {@code NotValidFieldsException}.
     *
     * @param ex the {@code NotValidFieldsException} object
     * @return the {@code CustomError} object
     */
    @ExceptionHandler(NotValidFieldsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleGiftCertificateFieldsNotValid(NotValidFieldsException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errorMessage = new StringBuilder();
        for (Object object : bindingResult.getAllErrors()) {
            if (object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;
                errorMessage.append(localeService.getLocaleMessage(fieldError.getCode()));
            }
        }
        return new CustomResponse(CustomCode.GIFT_CERTIFICATE_FIELDS_NOT_VALID.code,
                errorMessage.toString());
    }

}
