package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GiftCertificateValidatorTest {

    @Mock
    private Errors errors;
    private GiftCertificateValidator giftCertificateValidator;
    private GiftCertificateDto giftCertificate;

    @BeforeAll
    public void init(){
        giftCertificateValidator = new GiftCertificateValidator();
        MockitoAnnotations.openMocks(this);
        giftCertificate = new GiftCertificateDto("skydiving",
                "Try skydiving if you dont afraid!",
                new BigDecimal("60.70"), 100,
                LocalDateTime.of(2021, Month.FEBRUARY, 1, 0, 0),
                LocalDateTime.of(2021, Month.MARCH, 30, 0, 0),
                new HashSet<>());
    }

    @Test
    public void validateGiftCertificateTest(){
        giftCertificateValidator.validate(giftCertificate, errors);
        Mockito.verify(errors, Mockito.times(0)).reject(Mockito.anyString());
    }

    @Test
    public void validateNameEmptyTest(){
        giftCertificate.setName("");
        giftCertificateValidator.validate(giftCertificate, errors);
        Mockito.verify(errors, Mockito.times(1))
                .rejectValue("name", "name_empty");
    }

    @Test
    public void validateNameTooLongTest(){
        giftCertificate.setName("birthdayjsdkljjjjjjjjjjjjjjjjjjjj" +
                "jjjjjjjjjjjjjjjjjjjjjjjjjzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
                "zzzzzzzzzzzzzzzzzzzzzzzzzz");
        giftCertificateValidator.validate(giftCertificate, errors);
        Mockito.verify(errors, Mockito.times(1))
                .rejectValue("name", "name_too_long");
    }

    @Test
    public void validateDescriptionEmptyTest(){
        giftCertificate.setDescription("");
        giftCertificateValidator.validate(giftCertificate, errors);
        Mockito.verify(errors, Mockito.times(1))
                .rejectValue("description", "description_empty");
    }

    @Test
    public void validatePriceNotPositiveTest(){
        giftCertificate.setPrice(new BigDecimal("-89.00"));
        giftCertificateValidator.validate(giftCertificate, errors);
        Mockito.verify(errors, Mockito.times(1))
                .rejectValue("price", "price_not_positive");
    }

    @Test
    public void validateDurationNotPositiveTest(){
        giftCertificate.setDuration(-9);
        giftCertificateValidator.validate(giftCertificate, errors);
        Mockito.verify(errors, Mockito.times(1))
                .rejectValue("duration", "duration_not_positive");
    }

    @Test
    public void validateCreateDateInFutureTest(){
        giftCertificate.setCreateDate(LocalDateTime.of(2022, Month.FEBRUARY, 22, 0, 0));
        giftCertificateValidator.validate(giftCertificate, errors);
        Mockito.verify(errors, Mockito.times(1))
                .rejectValue("create_date", "create_date_in_future");
    }
}
