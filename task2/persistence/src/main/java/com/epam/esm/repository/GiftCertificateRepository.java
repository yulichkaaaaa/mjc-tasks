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
    void createGiftCertificate(GiftCertificate giftCertificate);

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
     * Find gift certificates by it's tags names.
     *
     * @param tagName names of tags
     */
    List<GiftCertificate> findGiftCertificateByTagName(String tagName);

    /**
     * Find gift certificates by it's name and description.
     * If only one of parameters passed, find by this parameter.
     *
     * @param name part of name of gift certificate
     * @param description part of description of gift certificate
     * @return list of gift certificates
     */
    List<GiftCertificate> findGiftCertificatesByNameAndDescription(String name, String description);
}
