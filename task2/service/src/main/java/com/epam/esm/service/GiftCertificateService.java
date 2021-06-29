package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GiftCertificateService {

    /**
     * Find gift certificate by it's id.
     *
     * @param giftCertificateId id of the gift certificate
     * @return the {@code GiftCertificateDto} object
     */
    GiftCertificateDto findGiftCertificateById(long giftCertificateId);

    /**
     * Create new Gift Certificate.
     *
     * @param giftCertificateDto the {@code GiftCertificateDto} object
     */
    void createGiftCertificate(GiftCertificateDto giftCertificateDto);

    /**
     * Update gift certificate.
     *
     * @param giftCertificateDto the {@code GiftCertificateDto} object
     */
    void updateGiftCertificate(GiftCertificateDto giftCertificateDto);

    /**
     * Delete gift certificate by it's id.
     *
     * @param giftCertificateId id of the gift certificate
     */
    void deleteGiftCertificateById(long giftCertificateId);

    /**
     * Find gift certificates by criteria.
     * For example, by tag, name and description.
     *
     * @param giftCertificateDto the {@code GiftCertificateDto} object
     * @return list of certificates
     */
    Page<GiftCertificateDto> findGiftCertificatesByCriteria(GiftCertificateDto giftCertificateDto,
                                                            List<String> sortCriteria,
                                                            String sortDirection,
                                                            Pageable pageable);
}
