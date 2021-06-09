package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converters.GiftCertificateDtoConverter;
import com.epam.esm.dto.converters.TagDtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GiftCertificateAlreadyExistsException;
import com.epam.esm.exception.GiftCertificateNotExistException;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateRepository giftCertificateRepository;
    private TagRepository tagRepository;
    private GiftCertificateDtoConverter giftCertificateDtoConverter;
    private TagDtoConverter tagDtoConverter;

    @Autowired
    public void setGiftCertificateRepository(GiftCertificateRepository giftCertificateRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Autowired
    public void setGiftCertificateDtoConverter(GiftCertificateDtoConverter giftCertificateDtoConverter) {
        this.giftCertificateDtoConverter = giftCertificateDtoConverter;
    }

    @Autowired
    public void setTagDtoConverter(TagDtoConverter tagDtoConverter) {
        this.tagDtoConverter = tagDtoConverter;
    }

    @Override
    public GiftCertificateDto findGiftCertificateById(long giftCertificateId) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateRepository
                .findGiftCertificateById(giftCertificateId);
        if(giftCertificateOptional.isEmpty()){
            throw new GiftCertificateNotFoundException(giftCertificateId);
        }
        GiftCertificate giftCertificate = giftCertificateOptional.get();
        Set<TagDto> tags = tagRepository
                .findTagsByGiftCertificateId(giftCertificateId)
                .stream()
                .map(tag -> tagDtoConverter.convertToDto(tag))
                .collect(Collectors.toSet());
        return giftCertificateDtoConverter.convertToDto(giftCertificate, tags);
    }

    @Transactional
    @Override
    public void createGiftCertificate(GiftCertificateDto giftCertificateDto) {
        long giftCertificateId = giftCertificateDto.getId();
        if(giftCertificateExists(giftCertificateId)){
            throw new GiftCertificateAlreadyExistsException(giftCertificateId);
        }
        GiftCertificate giftCertificate = giftCertificateDtoConverter.convertToEntity(giftCertificateDto);
        giftCertificateRepository.createGiftCertificate(giftCertificate);
        giftCertificateDto.getTags()
                .stream()
                .filter(tag -> tagRepository.findTagById(tag.getId()).isEmpty())
                .forEach(tag -> tagRepository.createTag(tagDtoConverter.convertToEntity(tag)));
    }

    @Transactional
    @Override
    public void updateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        long giftCertificateId = giftCertificateDto.getId();
        if(!giftCertificateExists(giftCertificateId)){
            throw new GiftCertificateNotExistException(giftCertificateId);
        }
        GiftCertificate giftCertificate = giftCertificateDtoConverter.convertToEntity(giftCertificateDto);
        giftCertificateRepository.createGiftCertificate(giftCertificate);
        giftCertificateDto.getTags()
                .stream()
                .filter(tag -> tagRepository.findTagById(tag.getId()).isEmpty())
                .forEach(tag -> tagRepository.createTag(tagDtoConverter.convertToEntity(tag)));
    }

    @Transactional
    @Override
    public void deleteGiftCertificateById(long giftCertificateId) {
        if(!giftCertificateExists(giftCertificateId)){
            throw new GiftCertificateNotExistException(giftCertificateId);
        }
        giftCertificateRepository.deleteGiftCertificate(giftCertificateId);
        tagRepository.disconnectTagsFromGiftCertificate(giftCertificateId);
    }

    @Override
    public List<GiftCertificateDto> findGiftCertificatesByTagName(TagDto tagDto) {
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

    private boolean giftCertificateExists(long giftCertificateId){
        boolean isGiftCertificateExists = false;
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateRepository
                .findGiftCertificateById(giftCertificateId);
        if(giftCertificateOptional.isPresent()){
            isGiftCertificateExists = true;
        }
        return isGiftCertificateExists;
    }
}
