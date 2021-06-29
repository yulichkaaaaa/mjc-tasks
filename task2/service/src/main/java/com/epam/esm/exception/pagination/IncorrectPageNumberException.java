package com.epam.esm.exception.pagination;

/**
 * Is thrown when attempt to pass incorrect page number (for example, negative).
 *
 * @author Shuleiko Yulia
 */
public class IncorrectPageNumberException extends RuntimeException {

    int pageNumber;

    /**
     * Constructs exception with given page number.
     *
     * @param pageNumber number of page
     */
    public IncorrectPageNumberException(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Getter method of page number.
     *
     * @return page number
     */
    public int getPageNumber() {
        return pageNumber;
    }
}
