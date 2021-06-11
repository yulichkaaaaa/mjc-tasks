package com.epam.esm.dto.converters;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TagDtoConverterTest {

    private TagDtoConverter tagDtoConverter;

    @BeforeAll
    public void init(){
        tagDtoConverter = new TagDtoConverter();
    }

    @Test
    public void convertToDtoTest(){
        long id = 12;
        String name = "birthday";
        Tag tag = new Tag(id, name);
        TagDto tagDto = new TagDto(id, name);
        Assertions.assertEquals(tagDto, tagDtoConverter.convertToDto(tag));
    }

    @Test
    public void convertToEntityTest(){
        long id = 12;
        String name = "birthday";
        Tag tag = new Tag(id, name);
        TagDto tagDto = new TagDto(id, name);
        Assertions.assertEquals(tag, tagDtoConverter.convertToEntity(tagDto));
    }

}
