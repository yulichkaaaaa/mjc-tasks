package com.epam.esm.dto.converters;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class GiftCertificateDtoConverter {

    public GiftCertificateDto convertToDto(GiftCertificate giftCertificate, Set<TagDto> tags){
        return new GiftCertificateDto(giftCertificate.getId(),
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(),
                tags);
    }

    public GiftCertificate convertToEntity(GiftCertificateDto giftCertificateDto){
        return new GiftCertificate(giftCertificateDto.getId(),
                giftCertificateDto.getName(),
                giftCertificateDto.getDescription(),
                giftCertificateDto.getPrice(),
                giftCertificateDto.getDuration(),
                giftCertificateDto.getCreateDate(),
                giftCertificateDto.getLastUpdateDate());
    }
}
