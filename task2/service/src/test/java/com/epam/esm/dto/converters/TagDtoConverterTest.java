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
    private Tag tag;
    private TagDto tagDto;

    @BeforeAll
    public void init() {
        tagDtoConverter = new TagDtoConverter();
        initTagData();
    }

    private void initTagData() {
        long id = 12;
        String name = "birthday";
        tag = new Tag(id, name);
        tagDto = new TagDto(id, name);
    }

    @Test
    public void convertToDtoTest() {
        Assertions.assertEquals(tagDto, tagDtoConverter.convertToDto(tag));
    }

    @Test
    public void convertToEntityTest() {
        Assertions.assertEquals(tag, tagDtoConverter.convertToEntity(tagDto));
    }

}
