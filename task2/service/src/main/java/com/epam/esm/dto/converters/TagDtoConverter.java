package com.epam.esm.dto.converters;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

/**
 * Converter of the tag's data transfer object class.
 *
 * @author Shuleiko Yulia
 */
@Component
public class TagDtoConverter {

    /**
     * Convert tag's entity to data transfer object.
     *
     * @param tag the {@code Tag} object
     * @return the {@code TagDto} object
     */
    public TagDto convertToDto(Tag tag){
        return new TagDto(tag.getId(),
                tag.getName());
    }

    /**
     * Convert tag's data transfer object to entity.
     *
     * @param tagDto the {@code TagDto} object
     * @return the {@code Tag} object
     */
    public Tag convertToEntity(TagDto tagDto){
        return new Tag(tagDto.getId(),
                tagDto.getName());
    }
}
