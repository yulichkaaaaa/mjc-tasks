package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.Optional;
import java.util.Set;

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
     * Find tags of the gift certificate by it's id.
     *
     * @param giftCertificateId id of the gift certificate
     * @return set of the {@code Tag} objects
     */
    Set<Tag> findTagsByGiftCertificateId(long giftCertificateId);
}
