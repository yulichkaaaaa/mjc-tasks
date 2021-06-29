package com.epam.esm.exception.pagination;

/**
 * Is thrown when attempt to pass too big page elements number (size).
 *
 * @author Shuleiko Yulia
 */
public class TooMuchPageElementsException extends RuntimeException {

    int elementsCount;

    /**
     * Constructs exception with given page elements count.
     *
     * @param elementsCount page elements count
     */
    public TooMuchPageElementsException(int elementsCount) {
        this.elementsCount = elementsCount;
    }

    /**
     * Getter method of page elements count.
     *
     * @return page elements count
     */
    public int getElementsCount() {
        return elementsCount;
    }
}
