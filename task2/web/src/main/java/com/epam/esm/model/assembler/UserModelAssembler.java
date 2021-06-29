package com.epam.esm.model.assembler;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDto;
import com.epam.esm.model.UserModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * Assemble representational model of user from data transfer object.
 *
 * @author Shuleiko Yulia
 */
@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<UserDto, UserModel> {

    /**
     * Creates a new {@link RepresentationModelAssemblerSupport} using
     * the given controller class and resource type.
     */
    public UserModelAssembler() {
        super(UserController.class, UserModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserModel toModel(UserDto userDto) {
        UserModel userModel = createResource(userDto);
        userModel.add(linkTo(methodOn(UserController.class).findUserById(userDto.getId())).withSelfRel());
        return userModel;
    }

    /**
     * Create the {@code UserModel} object from data transfer object.
     *
     * @param userDto the {@code UserDto} object
     * @return the {@code UserModel} object
     */
    private UserModel createResource(UserDto userDto) {
        return new UserModel(userDto.getId(),
                userDto.getName(),
                userDto.getEmail());
    }
}
