package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

/**
 * Interface provides operations with {@code TagDto} object.
 *
 * @author Shuleiko Yulia
 */
public interface TagService {

    /**
     * Create new tag.
     *
     * @param tagDto the {@code TagDto} object
     */
    void createTag(TagDto tagDto);

    /**
     * Delete tag by it's id.
     *
     * @param tagId id of the tag
     */
    void deleteTag(long tagId);

    /**
     * Find tag by it's id.
     *
     * @param tagId id of the tag
     * @return the {@code TagDto} object
     */
    TagDto findTagById(long tagId);
}
