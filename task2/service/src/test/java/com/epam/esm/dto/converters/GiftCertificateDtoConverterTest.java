package com.epam.esm.dto.converters;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GiftCertificateDtoConverterTest {

    private GiftCertificateDtoConverter giftCertificateDtoConverter;
    private GiftCertificateDto giftCertificateDto;
    private GiftCertificate giftCertificate;

    @BeforeAll
    public void init(){
        giftCertificateDtoConverter = new GiftCertificateDtoConverter();
        initGiftCertificateData();
    }

    private void initGiftCertificateData(){
        String name = "birthday";
        String description = "organization of birthday party";
        BigDecimal price = new BigDecimal("67.70");
        int duration = 14;
        LocalDateTime createDate = LocalDateTime.of(2020, Month.FEBRUARY, 2, 0, 0);
        LocalDateTime lastUpdateDate = LocalDateTime.now();
        giftCertificateDto = new GiftCertificateDto(name, description, price,
                duration, createDate, lastUpdateDate, new HashSet<>());
        giftCertificate = new GiftCertificate(name, description, price,
                duration, createDate, lastUpdateDate);
    }

    @Test
    public void convertToDtoTest(){
        Assertions.assertEquals(giftCertificateDto,
                giftCertificateDtoConverter.convertToDto(giftCertificate, new HashSet<>()));
    }

    @Test
    public void convertToEntityTest(){
        Assertions.assertEquals(giftCertificate,
                giftCertificateDtoConverter.convertToEntity(giftCertificateDto));
    }

}
