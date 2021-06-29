package com.epam.esm.exception.pagination;

/**
 * Is thrown when attempt to pass incorrect page size (for example, negative).
 *
 * @author Shuleiko Yulia
 */
public class IncorrectPageSizeException extends RuntimeException{

    int pageSize;

    /**
     * Constructs exception with given page size.
     *
     * @param pageSize size of page
     */
    public IncorrectPageSizeException(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Getter method of page size.
     *
     * @return page size
     */
    public int getPageSize() {
        return pageSize;
    }
}

