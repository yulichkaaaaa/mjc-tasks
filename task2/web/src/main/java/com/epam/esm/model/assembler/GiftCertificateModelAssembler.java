package com.epam.esm.model.assembler;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.GiftCertificateModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Assemble representational model of gift certificate from data transfer object.
 *
 * @author Shuleiko Yulia
 */
@Component
public class GiftCertificateModelAssembler extends RepresentationModelAssemblerSupport<GiftCertificateDto, GiftCertificateModel> {

    private TagModelAssembler tagModelAssembler;
    private static final String FIND_CERTIFICATES_LINK = "find certificates";

    /**
     * Creates a new {@link RepresentationModelAssemblerSupport} using
     * the given controller class and resource type.
     */
    public GiftCertificateModelAssembler(TagModelAssembler tagModelAssembler) {
        super(GiftCertificateController.class, GiftCertificateModel.class);
        this.tagModelAssembler = tagModelAssembler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificateModel toModel(GiftCertificateDto giftCertificateDto) {
        GiftCertificateModel giftCertificateModel = createResource(giftCertificateDto);
        giftCertificateModel.add(
                linkTo(methodOn(GiftCertificateController.class)
                        .findGiftCertificateById(giftCertificateModel.getId())).withSelfRel(),
                linkTo(methodOn(GiftCertificateController.class)
                        .findCertificates(null, null, null, null, null, 1, 5))
                        .withRel(FIND_CERTIFICATES_LINK)
        );
        return giftCertificateModel;
    }

    /**
     * Create the {@code GiftCertificateModel} object from data transfer object.
     *
     * @param giftCertificateDto the {@code GiftCertificateDto} object
     * @return the {@code GiftCertificateModel} object
     */
    private GiftCertificateModel createResource(GiftCertificateDto giftCertificateDto){
        return new GiftCertificateModel(giftCertificateDto.getId(),
                giftCertificateDto.getName(),
                giftCertificateDto.getDescription(),
                giftCertificateDto.getPrice(),
                giftCertificateDto.getDuration(),
                giftCertificateDto.getCreateDate(),
                giftCertificateDto.getLastUpdateDate(),
                giftCertificateDto.getTags()
                        .stream()
                        .map(tagDto -> tagModelAssembler.toModel(tagDto))
                        .collect(Collectors.toSet()));
    }
}
