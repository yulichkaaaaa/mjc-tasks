package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

/**
 * Interface provides operations with the storage of {@code GiftCertificate} entity.
 *
 * @author Shuleiko Yulia
 */
public interface GiftCertificateRepository {

    /**
     * Add new gift certificate to the storage.
     *
     * @param giftCertificate the {@code GiftCertificate} object
     */
    long createGiftCertificate(GiftCertificate giftCertificate);

    /**
     * Update gift certificate's properties.
     *
     * @param giftCertificate the {@code GiftCertificate} object
     */
    void updateGiftCertificate(GiftCertificate giftCertificate);

    /**
     * Delete gift certificate from storage.
     *
     * @param giftCertificateId id of the gift certificate
     */
    void deleteGiftCertificate(long giftCertificateId);

    /**
     * Find gift certificate by it's id.
     *
     * @param giftCertificateId id of the gift certificate
     * @return the {@code GiftCertificate} object
     */
    Optional<GiftCertificate> findGiftCertificateById(long giftCertificateId);

    /**
     * Find gift certificates that matches list of criteria:
     * name of the tag, name of the certificate and it's description.
     * Sort obtained certificates if sort criteria was given.
     *
     * @param tagNames      part of names of tags
     * @param name          part of name of gift certificate
     * @param description   part of description of gift certificate
     * @param sortCriteria  criteria of sorting (for example name, date)
     * @param sortDirection direction of sorting (asc or desc)
     * @param pageNumber    number of the page
     * @param pageSize      size of the page
     * @return list of gift certificates
     */
    List<GiftCertificate> findGiftCertificatesByCriteria(List<String> tagNames, String name, String description,
                                                         List<String> sortCriteria, String sortDirection,
                                                         int pageNumber, int pageSize);

}
