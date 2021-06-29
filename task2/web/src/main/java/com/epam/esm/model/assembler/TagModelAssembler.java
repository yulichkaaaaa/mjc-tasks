package com.epam.esm.model.assembler;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.TagModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Assemble representational model of tag from data transfer object.
 *
 * @author Shuleiko Yulia
 */
@Component
public class TagModelAssembler extends RepresentationModelAssemblerSupport<TagDto, TagModel> {

    /**
     * Creates a new {@link RepresentationModelAssemblerSupport} using
     * the given controller class and resource type.
     */
    public TagModelAssembler() {
        super(TagController.class, TagModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TagModel toModel(TagDto tagDto) {
        TagModel tagModel = createResource(tagDto);
        tagModel.add(
                linkTo(methodOn(TagController.class).findTagById(tagDto.getId())).withSelfRel()
        );
        return tagModel;
    }

    /**
     * Create the {@code TagModel} object from data transfer object.
     *
     * @param tagDto the {@code TagDto} object
     * @return the {@code TagModel} object
     */
    private TagModel createResource(TagDto tagDto) {
        return new TagModel(tagDto.getId(), tagDto.getName());
    }
}
