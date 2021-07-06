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

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 5;
    private static final String FIND_USERS_LINK = "findUsers";
    private static final String FIND_USER_ORDER_LINK = "findUserOrders";
    private static final String FIND_USER_MOST_POPULAR_TAG_LINK = "findUserMostPopularTag";

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
        userModel.add(linkTo(methodOn(UserController.class).findUserById(userDto.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class)
                        .findAllUsers(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE)).withRel(FIND_USERS_LINK),
                linkTo(methodOn(UserController.class)
                        .findAllUserOrders(userDto.getId(), DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE))
                        .withRel(FIND_USER_ORDER_LINK),
                linkTo(methodOn(UserController.class)
                        .findUserMostPopularTags(userDto.getId())).withRel(FIND_USER_MOST_POPULAR_TAG_LINK)
        );
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
