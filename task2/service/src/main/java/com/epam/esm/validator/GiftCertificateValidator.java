package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Gift certificate's validator.
 *
 * @author Shuleiko Yulia
 */
@Service
public class GiftCertificateValidator implements Validator {

    private static final int NAME_MAX_LENGTH = 40;
    private static final int DESCRIPTION_MAX_LENGTH = 200;
    private static final String EMPTY_NAME_ERROR = "name_empty";
    private static final String TOO_LONG_NAME_ERROR = "name_too_long";
    private static final String EMPTY_DESCRIPTION_ERROR = "description_empty";
    private static final String TOO_LONG_DESCRIPTION_ERROR = "description_too_long";
    private static final String NOT_POSITIVE_PRICE_ERROR = "price_not_positive";
    private static final String NOT_POSITIVE_DURATION_ERROR = "duration_not_positive";
    private static final String FUTURE_CREATE_DATE_ERROR = "create_date_in_future";

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return GiftCertificateDto.class.equals(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Object target, Errors errors) {
        GiftCertificateDto giftCertificate = (GiftCertificateDto) target;
        validateName(giftCertificate.getName(), errors);
        validateDescription(giftCertificate.getDescription(), errors);
        validatePrice(giftCertificate.getPrice(), errors);
        validateDuration(giftCertificate.getDuration(), errors);
        validateCreateDate(giftCertificate.getCreateDate(), errors);
    }

    private void validateName(String name, Errors errors){
        if(name.isEmpty()) {
            errors.reject(EMPTY_NAME_ERROR);
        } else if (name.length() > NAME_MAX_LENGTH) {
            errors.reject(TOO_LONG_NAME_ERROR);
        }
    }

    private void validateDescription(String description, Errors errors){
        if(description.isEmpty()) {
            errors.reject(EMPTY_DESCRIPTION_ERROR);
        } else if (description.length() > DESCRIPTION_MAX_LENGTH) {
            errors.reject(TOO_LONG_DESCRIPTION_ERROR);
        }
    }

    private void validatePrice(BigDecimal price, Errors errors){
        if(price.doubleValue() <= 0.0) {
            errors.reject(NOT_POSITIVE_PRICE_ERROR);
        }
    }

    private void validateDuration(int duration, Errors errors){
        if(duration <= 0) {
            errors.reject(NOT_POSITIVE_DURATION_ERROR);
        }
    }

    private void validateCreateDate(LocalDateTime createDate, Errors errors){
        if(createDate.isAfter(LocalDateTime.now())) {
            errors.reject(FUTURE_CREATE_DATE_ERROR);
        }
    }
}
