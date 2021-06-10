package com.epam.esm.error;

/**
 * Custom codes of errors.
 * First 3 digits represent response status.
 * Two last digits represent entity code:
 * 01 - tag, 02 - gift certificate,
 *
 * @author Shuleiko Yulia
 */
public enum CustomErrorCode {
    TAG_NOT_FOUND(40401),
    TAG_ALREADY_EXISTS(40001),
    TAG_NOT_EXIST(40001),
    TAG_FIELDS_NOT_VALID(40001),
    GIFT_CERTIFICATE_NOT_FOUND(40402),
    GIFT_CERTIFICATE_ALREADY_EXISTS(40002),
    GIFT_CERTIFICATE_NOT_EXIST(40002),
    GIFT_CERTIFICATE_FIELDS_NOT_VALID(40002);

    public final int code;

    /**
     * Construct the {@code ErrorCode} object with given custom code of error.
     *
     * @param code custom code of the error
     */
    CustomErrorCode(int code){
        this.code = code;
    }
}
