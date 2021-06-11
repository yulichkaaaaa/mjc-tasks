package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converters.TagDtoConverter;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.impl.JdbcTagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;
    private TagServiceImpl tagService;
    private TagDtoConverter tagDtoConverter;

    @BeforeAll
    public void init(){
        tagService = new TagServiceImpl();
        tagRepository = new JdbcTagRepository();
        tagDtoConverter = new TagDtoConverter();
        MockitoAnnotations.openMocks(this);
        tagService.setTagRepository(tagRepository);
        tagService.setTagDtoConverter(tagDtoConverter);
    }

    @Test
    public void createTagTest(){
        long id = 1;
        String name = "nature";
        Tag tag = new Tag(id, name);
        Mockito.when(tagRepository.findTagById(id)).thenReturn(Optional.empty());
        tagService.createTag(tagDtoConverter.convertToDto(tag));
        Mockito.verify(tagRepository, Mockito.times(1)).createTag(tag);
    }

    @Test
    public void createTagAlreadyExistTest(){
        long id = 1;
        String name = "nature";
        Tag tag = new Tag(id, name);
        Mockito.when(tagRepository.findTagById(id)).thenReturn(Optional.of(tag));
        Assertions.assertThrows(EntityAlreadyExistsException.class,
                () -> tagService.createTag(tagDtoConverter.convertToDto(tag)));
    }

    @Test
    public void findTagByIdTest(){
        long id = 1;
        String name = "nature";
        Tag tag = new Tag(1, name);
        Mockito.when(tagRepository.findTagById(id)).thenReturn(Optional.of(tag));
        TagDto foundTag = tagService.findTagById(id);
        Assertions.assertEquals(tag, tagDtoConverter.convertToEntity(foundTag));
    }

    @Test
    public void findTagByIdNotFoundTest(){
        long id = 10;
        Mockito.when(tagRepository.findTagById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> tagService.findTagById(id));
    }

    @Test
    public void deleteTagTest(){
        long id = 1;
        String name = "nature";
        Tag tag = new Tag(id, name);
        Mockito.when(tagRepository.findTagById(id)).thenReturn(Optional.of(tag));
        tagService.deleteTag(id);
        Mockito.verify(tagRepository, Mockito.times(1)).deleteTag(id);
    }

    @Test
    public void deleteTagNotExistTest(){
        long id = 10;
        Mockito.when(tagRepository.findTagById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotExistException.class, () -> tagService.deleteTag(id));
    }

}
