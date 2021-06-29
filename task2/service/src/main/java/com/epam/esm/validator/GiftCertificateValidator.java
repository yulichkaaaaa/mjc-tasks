package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Gift certificate's validator.
 *
 * @author Shuleiko Yulia
 */
@Service
public class GiftCertificateValidator implements Validator {

    private static final int NAME_MAX_LENGTH = 40;
    private static final int DESCRIPTION_MAX_LENGTH = 200;
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DURATION = "duration";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_PRICE = "price";
    private static final String FIELD_CREATE_DATE = "create_date";
    private static final String FIELD_TAGS = "tags";
    private static final String EMPTY_NAME_ERROR = "name_empty";
    private static final String TOO_LONG_NAME_ERROR = "name_too_long";
    private static final String EMPTY_DESCRIPTION_ERROR = "description_empty";
    private static final String TOO_LONG_DESCRIPTION_ERROR = "description_too_long";
    private static final String NOT_POSITIVE_PRICE_ERROR = "price_not_positive";
    private static final String NOT_POSITIVE_DURATION_ERROR = "duration_not_positive";
    private static final String FUTURE_CREATE_DATE_ERROR = "create_date_in_future";
    private static final String EMPTY_TAG_NAME_ERROR = "tag_name_empty";
    private static final String TOO_LONG_TAG_NAME_ERROR = "tag_name_too_long";

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
        if (Objects.nonNull(giftCertificate.getName())) {
            validateName(giftCertificate.getName(), errors);
        }
        if (Objects.nonNull(giftCertificate.getDescription())) {
            validateDescription(giftCertificate.getDescription(), errors);
        }
        if (Objects.nonNull(giftCertificate.getPrice())) {
            validatePrice(giftCertificate.getPrice(), errors);
        }
        if (Objects.nonNull(giftCertificate.getDuration())) {
            validateDuration(giftCertificate.getDuration(), errors);
        }
        if (Objects.nonNull(giftCertificate.getCreateDate())) {
            validateCreateDate(giftCertificate.getCreateDate(), errors);
        }
        if (Objects.nonNull(giftCertificate.getTags())) {
            giftCertificate.getTags().forEach(tag -> validateTagName(tag.getName(), errors));
        }
    }

    private void validateName(String name, Errors errors) {
        if (name.isEmpty()) {
            errors.rejectValue(FIELD_NAME, EMPTY_NAME_ERROR);
        } else if (name.length() > NAME_MAX_LENGTH) {
            errors.rejectValue(FIELD_NAME, TOO_LONG_NAME_ERROR);
        }
    }

    private void validateDescription(String description, Errors errors) {
        if (description.isEmpty()) {
            errors.rejectValue(FIELD_DESCRIPTION, EMPTY_DESCRIPTION_ERROR);
        } else if (description.length() > DESCRIPTION_MAX_LENGTH) {
            errors.rejectValue(FIELD_DESCRIPTION, TOO_LONG_DESCRIPTION_ERROR);
        }
    }

    private void validatePrice(BigDecimal price, Errors errors) {
        if (price.doubleValue() <= 0.0) {
            errors.rejectValue(FIELD_PRICE, NOT_POSITIVE_PRICE_ERROR);
        }
    }

    private void validateDuration(int duration, Errors errors) {
        if (duration <= 0) {
            errors.rejectValue(FIELD_DURATION, NOT_POSITIVE_DURATION_ERROR);
        }
    }

    private void validateCreateDate(LocalDateTime createDate, Errors errors) {
        if (createDate.isAfter(LocalDateTime.now())) {
            errors.rejectValue(FIELD_CREATE_DATE, FUTURE_CREATE_DATE_ERROR);
        }
    }

    private void validateTagName(String name, Errors errors) {
        if (name.isEmpty()) {
            errors.rejectValue(FIELD_TAGS, EMPTY_TAG_NAME_ERROR);
        } else if (name.length() > NAME_MAX_LENGTH) {
            errors.rejectValue(FIELD_TAGS, TOO_LONG_TAG_NAME_ERROR);
        }
    }
}
