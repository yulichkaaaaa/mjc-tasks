package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converters.TagDtoConverter;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the {@code TagService}.
 *
 * @author Shuleiko Yulia
 */
@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;
    private TagDtoConverter tagDtoConverter;

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
    public void createTag(TagDto tagDto) {
        String name = tagDto.getName();
        Optional<Tag> tagOptional = tagRepository.findTagByName(name);
        if(tagOptional.isPresent()) {
            throw new EntityAlreadyExistsException(tagOptional.get().getId());
        }
        Tag tag = tagDtoConverter.convertToEntity(tagDto);
        tagRepository.createTag(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteTag(long tagId) {
        Optional<Tag> tagOptional = tagRepository.findTagById(tagId);
        if(tagOptional.isEmpty()) {
            throw new EntityNotExistException(tagId);
        }
        tagRepository.disconnectGiftCertificatesFromTag(tagId);
        tagRepository.deleteTag(tagId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TagDto findTagById(long tagId) {
        Optional<Tag> tagOptional = tagRepository.findTagById(tagId);
        if(tagOptional.isEmpty()) {
            throw new EntityNotFoundException(tagId);
        }
        return tagDtoConverter.convertToDto(tagOptional.get());
    }
}
