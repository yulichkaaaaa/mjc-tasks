package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converters.GiftCertificateDtoConverter;
import com.epam.esm.dto.converters.TagDtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.EntityNotExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
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
        Set<TagDto> tags = tagRepository
                .findTagsByGiftCertificateId(giftCertificateId)
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
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        long giftCertificateId = giftCertificateRepository.createGiftCertificate(giftCertificate);
        updateTags(giftCertificateDto.getTags(), giftCertificateId);
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
            tagRepository.disconnectTagsFromGiftCertificate(giftCertificateId);
            updateTags(giftCertificateDto.getTags(), giftCertificateId);
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
        tagRepository.disconnectTagsFromGiftCertificate(giftCertificateId);
        giftCertificateRepository.deleteGiftCertificate(giftCertificateId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificateDto> findGiftCertificatesByCriteria(GiftCertificateDto giftCertificateDto) {
        List<GiftCertificateDto> giftCertificates = new ArrayList<>();
        if (!giftCertificateDto.getTags().isEmpty()) {
            giftCertificates = findGiftCertificatesByTagName(giftCertificateDto.getTag(0));
        }
        if (giftCertificateDto.getName() != null || giftCertificateDto.getDescription() != null) {
            List<GiftCertificateDto> foundGiftCertificates
                    = findGiftCertificatesByNameAndDescription(giftCertificateDto);
            if (giftCertificates.isEmpty()) {
                giftCertificates = foundGiftCertificates;
            } else {
                giftCertificates = giftCertificates
                        .stream()
                        .filter(foundGiftCertificates::contains)
                        .collect(Collectors.toList());
            }
        }
        return giftCertificates;
    }

    /**
     * Insert new tags into the storage and create connections between certificate and tag.
     *
     * @param tags              set of tags
     * @param giftCertificateId id of gift certificate
     */
    private void updateTags(Set<TagDto> tags, long giftCertificateId) {
        tags.stream()
                .filter(tag -> tagRepository.findTagByName(tag.getName()).isEmpty())
                .forEach(tag -> tagRepository.createTag(tagDtoConverter.convertToEntity(tag)));
        tags.forEach(tag -> tag.setId(tagRepository.findTagByName(tag.getName()).get().getId()));
        tags.stream()
                .filter(tag -> !tagRepository.connectionExists(giftCertificateId, tag.getId()))
                .forEach(tag -> tagRepository.connectTagsToGiftCertificate(giftCertificateId, tag.getId()));
    }

    /**
     * Find all gift certificates.
     *
     * @return list of the certificates
     */
    private List<GiftCertificateDto> findAllGiftCertificates() {
        return giftCertificateRepository
                .findAllGiftCertificates()
                .stream()
                .map(giftCertificate ->
                        giftCertificateDtoConverter.convertToDto(giftCertificate, tagRepository
                                .findTagsByGiftCertificateId(giftCertificate.getId())
                                .stream()
                                .map(tag -> tagDtoConverter.convertToDto(tag))
                                .collect(Collectors.toSet()))
                )
                .collect(Collectors.toList());
    }

    /**
     * Find certificates by tag's name.
     *
     * @param tagDto the {@code TagDto} object
     * @return list of the certificates
     */
    private List<GiftCertificateDto> findGiftCertificatesByTagName(TagDto tagDto) {
        return giftCertificateRepository
                .findGiftCertificateByTagName(tagDto.getName())
                .stream()
                .map(giftCertificate ->
                        giftCertificateDtoConverter.convertToDto(giftCertificate, tagRepository
                                .findTagsByGiftCertificateId(giftCertificate.getId())
                                .stream()
                                .map(tag -> tagDtoConverter.convertToDto(tag))
                                .collect(Collectors.toSet()))
                )
                .collect(Collectors.toList());
    }

    /**
     * Find certificated by it's name and description parts.
     *
     * @param giftCertificateDto the {@code GiftCertificateDto} object
     * @return list of the certificates
     */
    private List<GiftCertificateDto> findGiftCertificatesByNameAndDescription(GiftCertificateDto giftCertificateDto) {
        return giftCertificateRepository
                .findGiftCertificatesByNameAndDescription(giftCertificateDto.getName(), giftCertificateDto.getDescription())
                .stream()
                .map(giftCertificate ->
                        giftCertificateDtoConverter.convertToDto(giftCertificate, tagRepository
                                .findTagsByGiftCertificateId(giftCertificate.getId())
                                .stream()
                                .map(tag -> tagDtoConverter.convertToDto(tag))
                                .collect(Collectors.toSet()))
                )
                .collect(Collectors.toList());
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
