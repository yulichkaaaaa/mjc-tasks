package com.epam.esm.dto.converters;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Converter of the gift certificate's data transfer object class.
 *
 * @author Shuleiko Yulia
 */
@Component
public class GiftCertificateDtoConverter {

    /**
     * Convert gift certificate's entity to data transfer object.
     *
     * @param giftCertificate the {@code GiftCertificate} object
     * @param tags            set of tags
     * @return the {@code GiftCertificateDto} object
     */
    public GiftCertificateDto convertToDto(GiftCertificate giftCertificate, Set<TagDto> tags) {
        return new GiftCertificateDto(giftCertificate.getId(),
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(),
                tags);
    }

    /**
     * Convert gift certificate's data transfer object to entity.
     *
     * @param giftCertificateDto the {@code GiftCertificateDto} object
     * @return the {@code GiftCertificate} object
     */
    public GiftCertificate convertToEntity(GiftCertificateDto giftCertificateDto) {
        return new GiftCertificate(giftCertificateDto.getId(),
                giftCertificateDto.getName(),
                giftCertificateDto.getDescription(),
                giftCertificateDto.getPrice(),
                giftCertificateDto.getDuration(),
                giftCertificateDto.getCreateDate(),
                giftCertificateDto.getLastUpdateDate());
    }
}
