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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
     * Construct gift certificates service with all necessary dependencies.
     */
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository,
                                      TagRepository tagRepository,
                                      GiftCertificateDtoConverter giftCertificateDtoConverter,
                                      TagDtoConverter tagDtoConverter) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
        this.giftCertificateDtoConverter = giftCertificateDtoConverter;
        this.tagDtoConverter = tagDtoConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificateDto findGiftCertificateById(long giftCertificateId) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateRepository
                .findGiftCertificateById(giftCertificateId);
        if (giftCertificateOptional.isEmpty() || giftCertificateOptional.get().isDeleted()) {
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
    public long createGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateDtoConverter
                .convertToEntity(giftCertificateDto);
        updateTags(giftCertificateDto.getTags(), giftCertificate);
        return giftCertificateRepository.createGiftCertificate(giftCertificate);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void updateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        long giftCertificateId = giftCertificateDto.getId();
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateRepository
                .findGiftCertificateById(giftCertificateId);
        if (giftCertificateOptional.isEmpty() || giftCertificateOptional.get().isDeleted()) {
            throw new EntityNotExistException(giftCertificateId);
        }
        GiftCertificate giftCertificate = giftCertificateOptional.get();
        giftCertificateRepository
                .updateGiftCertificate(updateGiftCertificateFields(giftCertificate, giftCertificateDto));
        updateTags(giftCertificateDto.getTags(), giftCertificate);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteGiftCertificateById(long giftCertificateId) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateRepository
                .findGiftCertificateById(giftCertificateId);
        if (giftCertificateOptional.isEmpty() || giftCertificateOptional.get().isDeleted()) {
            throw new EntityNotExistException(giftCertificateId);
        }
        GiftCertificate giftCertificate = giftCertificateOptional.get();
        giftCertificate.setDeleted(true);
        giftCertificateRepository.updateGiftCertificate(giftCertificate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<GiftCertificateDto> findGiftCertificatesByCriteria(GiftCertificateDto giftCertificateDto,
                                                                   List<String> sortCriteria,
                                                                   String sortDirection,
                                                                   Pageable pageable) {
        List<String> tagNames = giftCertificateDto.getTags().stream().map(TagDto::getName).collect(Collectors.toList());
        List<GiftCertificate> giftCertificates = giftCertificateRepository
                .findGiftCertificatesByCriteria(tagNames, giftCertificateDto.getName(),
                        giftCertificateDto.getDescription(), sortCriteria, sortDirection,
                        pageable.getPageNumber(), pageable.getPageSize());
        List<GiftCertificateDto> giftCertificateDtos = giftCertificates
                .stream()
                .map(giftCertificate -> giftCertificateDtoConverter.convertToDto(giftCertificate, giftCertificate.getTags()
                        .stream()
                        .map(tag -> tagDtoConverter.convertToDto(tag))
                        .collect(Collectors.toSet())))
                .collect(Collectors.toList());
        return new PageImpl<>(giftCertificateDtos, pageable, giftCertificates.size());
    }

    /**
     * Insert new tags into the storage and create connections between certificate and tag.
     *
     * @param tagDtos         set of tags
     * @param giftCertificate the {@code GiftCertificate} object
     */
    private void updateTags(Set<TagDto> tagDtos, GiftCertificate giftCertificate) {
        tagDtos.stream()
                .filter(tag -> tagRepository.findTagByName(tag.getName()).isEmpty())
                .forEach(tag -> tagRepository.createTag(tagDtoConverter.convertToEntity(tag)));
        Set<Tag> tags = tagDtos
                .stream()
                .map(tagDto -> tagDtoConverter.convertToEntity(tagDto))
                .collect(Collectors.toSet());
        tags.forEach(tag -> tag.setId(tagRepository.findTagByName(tag.getName()).get().getId()));
        giftCertificate.setTags(tags);
    }

    /**
     * Update fields of the {@code GiftCertificate} object
     * with non null fields of the {@code GiftCertificateDto} object.
     *
     * @param giftCertificate    the {@code GiftCertificate} object from the storage
     * @param giftCertificateDto the {@code GiftCertificateDto} object passed by request
     * @return the {@code GiftCertificate} object with new fields
     */
    private GiftCertificate updateGiftCertificateFields(GiftCertificate giftCertificate,
                                                        GiftCertificateDto giftCertificateDto) {
        if (Objects.nonNull(giftCertificateDto.getName())) {
            giftCertificate.setName(giftCertificateDto.getName());
        }
        if (Objects.nonNull(giftCertificateDto.getDescription())) {
            giftCertificate.setDescription(giftCertificateDto.getDescription());
        }
        if (Objects.nonNull(giftCertificateDto.getPrice())) {
            giftCertificate.setPrice(giftCertificateDto.getPrice());
        }
        if (Objects.nonNull(giftCertificateDto.getDuration())) {
            giftCertificate.setDuration(giftCertificateDto.getDuration());
        }
        return giftCertificate;
    }
}
