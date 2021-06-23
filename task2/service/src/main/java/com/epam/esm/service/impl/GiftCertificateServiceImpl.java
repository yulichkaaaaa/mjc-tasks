package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converters.GiftCertificateDtoConverter;
import com.epam.esm.dto.converters.TagDtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.LocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of the {@code GiftCertificateService}.
 *
 * @author Shuleiko Yulia
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateRepository giftCertificateRepository;
    private TagRepository tagRepository;
    private GiftCertificateDtoConverter giftCertificateDtoConverter;
    private TagDtoConverter tagDtoConverter;

    /**
     * Setter method of the {@code GiftCertificateRepository} object
     *
     * @param giftCertificateRepository the {@code GiftCertificateRepository} object
     */
    @Autowired
    public void setGiftCertificateRepository(GiftCertificateRepository giftCertificateRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
    }

    /**
     * Setter method of the {@code TagRepository} object
     *
     * @param tagRepository the {@code TagRepository} object
     */
    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    /**
     * Setter method of the {@code GiftCertificateDtoConverter} object
     *
     * @param giftCertificateDtoConverter the {@code GiftCertificateDtoConverter} object
     */
    @Autowired
    public void setGiftCertificateDtoConverter(GiftCertificateDtoConverter giftCertificateDtoConverter) {
        this.giftCertificateDtoConverter = giftCertificateDtoConverter;
    }

    /**
     * Setter method of the {@code TagDtoConverter} object
     *
     * @param tagDtoConverter the {@code TagDtoConverter} object
     */
    @Autowired
    public void setTagDtoConverter(TagDtoConverter tagDtoConverter) {
        this.tagDtoConverter = tagDtoConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificateDto findGiftCertificateById(long giftCertificateId) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateRepository
                .findGiftCertificateById(giftCertificateId);
        if (giftCertificateOptional.isEmpty()) {
            throw new EntityNotFoundException(giftCertificateId);
        }
        GiftCertificate giftCertificate = giftCertificateOptional.get();
        Set<TagDto> tags = giftCertificate.getTags()
                .stream()
                .map(tag -> tagDtoConverter.convertToDto(tag))
                .collect(Collectors.toSet());
        return giftCertificateDtoConverter.convertToDto(giftCertificate, tags);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void createGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateDtoConverter.convertToEntity(giftCertificateDto);
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setId(giftCertificateRepository.createGiftCertificate(giftCertificate));
        updateTags(giftCertificateDto.getTags(), giftCertificate);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void updateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        long giftCertificateId = giftCertificateDto.getId();
        if (!giftCertificateExists(giftCertificateId)) {
            throw new EntityNotExistException(giftCertificateId);
        }
        GiftCertificate giftCertificate = giftCertificateDtoConverter.convertToEntity(giftCertificateDto);
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        giftCertificateRepository.updateGiftCertificate(giftCertificate);
        if (!giftCertificateDto.getTags().isEmpty()) {
            //tagRepository.disconnectTagsFromGiftCertificate(giftCertificateId);
            //updateTags(giftCertificateDto.getTags(), giftCertificateId);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteGiftCertificateById(long giftCertificateId) {
        if (!giftCertificateExists(giftCertificateId)) {
            throw new EntityNotExistException(giftCertificateId);
        }
        //tagRepository.disconnectTagsFromGiftCertificate(giftCertificateId);
        giftCertificateRepository.deleteGiftCertificate(giftCertificateId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificateDto> findGiftCertificatesByCriteria(GiftCertificateDto giftCertificateDto,
                                                                   List<String> sortCriteria,
                                                                   String sortDirection) {
        String tagName = giftCertificateDto.getTag(0).getName();
        List<GiftCertificate> giftCertificates = giftCertificateRepository
                .findGiftCertificatesByCriteria(tagName, giftCertificateDto.getName(),
                        giftCertificateDto.getDescription(), sortCriteria, sortDirection);
        return giftCertificates
                .stream()
                .map(giftCertificate -> giftCertificateDtoConverter.convertToDto(giftCertificate, giftCertificate.getTags()
                        .stream()
                        .map(tag -> tagDtoConverter.convertToDto(tag))
                        .collect(Collectors.toSet())))
                .collect(Collectors.toList());
    }

    /**
     * Insert new tags into the storage and create connections between certificate and tag.
     *
     * @param tags set of tags
     * @param giftCertificate the {@code GiftCertificate} object
     */
    private void updateTags(Set<TagDto> tags, GiftCertificate giftCertificate) {
        tags.stream()
                .filter(tag -> tagRepository.findTagByName(tag.getName()).isEmpty())
                .forEach(tag -> tagRepository.createTag(tagDtoConverter.convertToEntity(tag)));
        tags.forEach(tag -> tag.setId(tagRepository.findTagByName(tag.getName()).get().getId()));
        tags.stream()
                .map(tagDto -> tagDtoConverter.convertToEntity(tagDto))
                .forEach(giftCertificate::addTag);
    }

    /**
     * Define if certificate is in storage.
     *
     * @param giftCertificateId id of the certificate
     * @return {@code true} if certificate exists in storage
     */
    private boolean giftCertificateExists(long giftCertificateId) {
        boolean isGiftCertificateExists = false;
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateRepository
                .findGiftCertificateById(giftCertificateId);
        if (giftCertificateOptional.isPresent()) {
            isGiftCertificateExists = true;
        }
        return isGiftCertificateExists;
    }
}
