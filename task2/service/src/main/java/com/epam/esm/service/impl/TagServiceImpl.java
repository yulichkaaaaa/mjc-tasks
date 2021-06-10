package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converters.TagDtoConverter;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.impl.JdbcTagRepository;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private JdbcTagRepository jdbcTagRepository;
    private TagDtoConverter tagDtoConverter;

    @Autowired
    public void setJdbcTagRepository(JdbcTagRepository jdbcTagRepository) {
        this.jdbcTagRepository = jdbcTagRepository;
    }

    @Autowired
    public void setTagDtoConverter(TagDtoConverter tagDtoConverter) {
        this.tagDtoConverter = tagDtoConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createTag(TagDto tagDto) {
        long tagId = tagDto.getId();
        Optional<Tag> tagOptional = jdbcTagRepository.findTagById(tagId);
        if(tagOptional.isPresent()) {
            throw new EntityAlreadyExistsException(tagId);
        }
        Tag tag = tagDtoConverter.convertToEntity(tagDto);
        jdbcTagRepository.createTag(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteTag(long tagId) {
        Optional<Tag> tagOptional = jdbcTagRepository.findTagById(tagId);
        if(tagOptional.isEmpty()) {
            throw new EntityNotExistException(tagId);
        }
        jdbcTagRepository.deleteTag(tagId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TagDto findTagById(long tagId) {
        Optional<Tag> tagOptional = jdbcTagRepository.findTagById(tagId);
        if(tagOptional.isEmpty()) {
            throw new EntityNotFoundException(tagId);
        }
        return tagDtoConverter.convertToDto(tagOptional.get());
    }
}
