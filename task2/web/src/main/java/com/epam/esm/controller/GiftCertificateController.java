package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.LocaleService;
import com.epam.esm.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("gift-certificate")
public class GiftCertificateController {

    private GiftCertificateService giftCertificateService;
    private GiftCertificateValidator giftCertificateValidator;
    private LocaleService localeService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(giftCertificateValidator);
    }

    @Autowired
    public void setGiftCertificateService(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @Autowired
    public void setGiftCertificateValidator(GiftCertificateValidator giftCertificateValidator) {
        this.giftCertificateValidator = giftCertificateValidator;
    }

    @Autowired
    public void setLocaleService(LocaleService localeService) {
        this.localeService = localeService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public GiftCertificateDto findGiftCertificateById(@PathVariable long id){
        return giftCertificateService.findGiftCertificateById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public void findGiftCertificateBy(){
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGiftCertificateById(@PathVariable long id){
        giftCertificateService.deleteGiftCertificateById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto createGiftCertificate(@PathVariable long id,
                                      @Valid GiftCertificateDto giftCertificateDto,
                                                    BindingResult bindingResult){
        if (!bindingResult.hasErrors()) {
            bindingResult.getAllErrors();
            giftCertificateDto.setId(id);
            giftCertificateService.createGiftCertificate(giftCertificateDto);
        }
        return giftCertificateDto;
    }

    @RequestMapping(value = "{/id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public GiftCertificateDto updateGiftCertificate(@PathVariable long id,
                                      @Valid GiftCertificateDto giftCertificateDto){
        giftCertificateDto.setId(id);
        giftCertificateService.updateGiftCertificate(giftCertificateDto);
        return giftCertificateDto;
    }
    
}
