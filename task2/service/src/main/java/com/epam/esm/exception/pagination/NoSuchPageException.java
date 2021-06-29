package com.epam.esm.exception.pagination;

/**
 * Is thrown when there is no page with passed number,
 * because number of page is more than number of last page.
 *
 * @author Shuleiko Yulia
 */
public class NoSuchPageException extends RuntimeException {

    int pageNumber;

    /**
     * Constructs exception with given page number.
     *
     * @param pageNumber size of page
     */
    public NoSuchPageException(int pageNumber) {
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
