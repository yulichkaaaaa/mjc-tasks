package com.epam.esm.response;

/**
 * Custom codes of response.
 * First 3 digits represent response status.
 * Two last digits represent entity code:
 * 00 - general, 01 - tag, 02 - gift certificate,
 * 03 - user, 04 - order.
 *
 * @author Shuleiko Yulia
 */
public enum CustomCode {

    TAG_WAS_CREATED(20101),
    GIFT_CERTIFICATE_WAS_CREATED(20102),
    ORDER_WAS_CREATED(20104),
    GIFT_CERTIFICATE_WAS_UPDATED(20402),
    GIFT_CERTIFICATE_WAS_DELETED(20402),
    TAG_WAS_DELETED(20401),
    TAG_NOT_FOUND(40401),
    TAG_NOT_EXIST(40001),
    TAG_FIELDS_NOT_VALID(40001),
    GIFT_CERTIFICATE_NOT_FOUND(40402),
    GIFT_CERTIFICATE_ALREADY_EXISTS(40002),
    GIFT_CERTIFICATE_NOT_EXIST(40002),
    GIFT_CERTIFICATE_FIELDS_NOT_VALID(40002),
    USER_NOT_FOUND(40403),
    USER_NOT_EXIST(40403),
    INCORRECT_PAGE_NUMBER(40000),
    INCORRECT_PAGE_SIZE(40000),
    TOO_MUCH_PAGE_ELEMENTS(40000),
    NO_SUCH_PAGE_EXCEPTION(40400);

    public final int code;

    /**
     * Construct the {@code ErrorCode} object with given custom code of error.
     *
     * @param code custom code of the error
     */
    CustomCode(int code) {
        this.code = code;
    }
}
