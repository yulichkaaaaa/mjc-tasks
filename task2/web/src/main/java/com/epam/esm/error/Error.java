package com.epam.esm.error;

/**
 * Error entity.
 *
 * @author Shuleiko Yulia
 */
public class Error {

    private int code;
    private String message;

    /**
     * Constructs error with given code and message.
     * @param code error's code
     * @param message error's message
     */
    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Getter method of the error's code.
     *
     * @return error's code
     */
    public int getCode() {
        return code;
    }

    /**
     * Getter method of the error's message.
     *
     * @return error's message
     */
    public String getMessage() {
        return message;
    }
}
