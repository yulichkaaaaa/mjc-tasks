package com.epam.esm.dto.converters;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagDtoConverter {

    public TagDto convertToDto(Tag tag){
        return new TagDto(tag.getId(),
                tag.getName());
    }

    public Tag convertToEntity(TagDto tagDto){
        return new Tag(tagDto.getId(),
                tagDto.getName());
    }
}
