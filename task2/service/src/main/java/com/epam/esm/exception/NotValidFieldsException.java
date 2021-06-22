package com.epam.esm.exception;

import org.springframework.validation.BindingResult;

/**
 * Is thrown when given from client fields aren't valid.
 *
 * @author Shuleiko Yulia
 */
public class NotValidFieldsException extends RuntimeException {

    private BindingResult bindingResult;

    /**
     * Constructs exception with given binding result.
     *
     * @param bindingResult the {@code BindingResult} object
     */
    public NotValidFieldsException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    /**
     * Getter method of binding result.
     *
     * @return the {@code BindingResult} object
     */
    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
