package com.epam.esm.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;

public class CustomPageImpl<T> extends PageImpl<T> {

    private final long total;

    /**
     * Constructor of {@code PageImpl}.
     *
     * @param content the content of this page, must not be {@literal null}.
     * @param pageable the paging information, must not be {@literal null}.
     * @param total the total amount of items available. The total might be adapted considering the length of the content
     *          given, if it is going to be the content of the last page. This is in place to mitigate inconsistencies.
     */
    public CustomPageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
        this.total = total;
    }

    @Override
    public int getTotalPages() {
        return getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize());
    }

    @Override
    public long getTotalElements() {
        return total;
    }
    @Override
    public boolean hasNext() {
        return getNumber() + 1 < getTotalPages();
    }

    @Override
    public boolean isLast() {
        return !hasNext();
    }

    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        return new CustomPageImpl<>(getConvertedContent(converter), getPageable(), total);
    }
}
