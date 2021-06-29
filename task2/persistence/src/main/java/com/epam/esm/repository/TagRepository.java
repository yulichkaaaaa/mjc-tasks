package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.Optional;

/**
 * Interface provides operations with the storage of {@code Tag} entity.
 *
 * @author Shuleiko Yulia
 */
public interface TagRepository {

    /**
     * Add new tag to the storage.
     *
     * @param tag the {@code Tag} object
     */
    void createTag(Tag tag);

    /**
     * Delete tag from storage.
     *
     * @param tagId id of the tag
     */
    void deleteTag(long tagId);

    /**
     * Find tag by it's id.
     *
     * @param tagId id of the tag
     * @return the {@code Tag} object
     */
    Optional<Tag> findTagById(long tagId);

    /**
     * Find tag by it's name.
     *
     * @param name name of the tag
     */
    Optional<Tag> findTagByName(String name);
}
