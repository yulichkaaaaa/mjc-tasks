package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificateDto findGiftCertificateById(long giftCertificateId);
    void createGiftCertificate(GiftCertificateDto giftCertificateDto);
    void updateGiftCertificate(GiftCertificateDto giftCertificateDto);
    void deleteGiftCertificateById(long giftCertificateId);
    List<GiftCertificateDto> findGiftCertificatesByTagName(TagDto tagDto);
    //List<GiftCertificateDto> findGiftCertificatesByCriteria();
}
