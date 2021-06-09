package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("gift-certificate")
public class GiftCertificateController {

    private GiftCertificateService giftCertificateService;

    @Autowired
    public void setGiftCertificateService(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
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

    @RequestMapping(value = "{/id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createGiftCertificate(@PathVariable long id,
                                      @Valid GiftCertificateDto giftCertificateDto,
                                      Errors errors){
        if (errors.hasErrors()) {
            //throw new
        }
        giftCertificateDto.setId(id);
        giftCertificateService.createGiftCertificate(giftCertificateDto);
    }

    @RequestMapping(value = "{/id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGiftCertificate(@PathVariable long id,
                                      @Valid GiftCertificateDto giftCertificateDto){
        giftCertificateDto.setId(id);
        giftCertificateService.updateGiftCertificate(giftCertificateDto);
    }

    /*@ExceptionHandler(GiftCertificateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error spittleNotFound(SpittleNotFoundException e) {
        long spittleId = e.getSpittleId();
        return new Error(4, "Spittle [" + spittleId + "] not found");
    }*/
}
