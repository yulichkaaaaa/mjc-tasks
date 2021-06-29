package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.error.CustomError;
import com.epam.esm.error.CustomErrorCode;
import com.epam.esm.exception.EntityNotExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.pagination.NoSuchPageException;
import com.epam.esm.model.OrderModel;
import com.epam.esm.model.TagModel;
import com.epam.esm.model.UserModel;
import com.epam.esm.model.assembler.OrderModelAssembler;
import com.epam.esm.model.assembler.TagModelAssembler;
import com.epam.esm.model.assembler.UserModelAssembler;
import com.epam.esm.service.LocaleService;
import com.epam.esm.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Rest controller that processes requests with the users.
 *
 * @author Shuleiko Yulia
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private static final String ENTITY_NOT_FOUND_ERROR = "entity_not_found";
    private static final String ENTITY_NOT_EXIST_ERROR = "entity_not_exist";
    private UserService userService;
    private LocaleService localeService;
    private PagedResourcesAssembler<UserDto> pagedUserResourcesAssembler;
    private PagedResourcesAssembler<OrderDto> pagedOrderResourcesAssembler;
    private UserModelAssembler userModelAssembler;
    private OrderModelAssembler orderModelAssembler;
    private TagModelAssembler tagModelAssembler;

    /**
     * Construct controller with all necessary dependencies.
     */
    public UserController(UserService userService,
                          LocaleService localeService,
                          PagedResourcesAssembler<UserDto> pagedUserResourcesAssembler,
                          UserModelAssembler userModelAssembler,
                          PagedResourcesAssembler<OrderDto> pagedOrderResourcesAssembler,
                          OrderModelAssembler orderModelAssembler,
                          TagModelAssembler tagModelAssembler) {
        this.userService = userService;
        this.localeService = localeService;
        this.pagedUserResourcesAssembler = pagedUserResourcesAssembler;
        this.userModelAssembler = userModelAssembler;
        this.pagedOrderResourcesAssembler = pagedOrderResourcesAssembler;
        this.orderModelAssembler = orderModelAssembler;
        this.tagModelAssembler = tagModelAssembler;
    }

    /**
     * Find user by it's id.
     *
     * @param id id of the user
     * @return the {@code UserModel} object
     */
    @GetMapping(value = "/{id}")
    public UserModel findUserById(@PathVariable long id) {
        return userModelAssembler.toModel(userService.findUserById(id));
    }

    /**
     * Find all users.
     *
     * @param page page number
     * @param size page elements count
     * @return the {@code PagedModel<UserModel>} object
     */
    @GetMapping
    public PagedModel<UserModel> findAllUsers(@RequestParam int page,
                                              @RequestParam int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<UserDto> usersPage = userService.findAllUsers(paging);
        if (usersPage.getNumber() >= usersPage.getTotalPages()) {
            throw new NoSuchPageException(usersPage.getNumber());
        }
        return pagedUserResourcesAssembler.toModel(usersPage, userModelAssembler);
    }

    /**
     * Find all user's orders.
     *
     * @param id   id of user
     * @param page page number
     * @param size page elements count
     * @return the {@code PagedModel<OrderModel>} object
     */
    @GetMapping(value = "/{id}/orders")
    public PagedModel<OrderModel> findAllUserOrders(@PathVariable long id,
                                                    @RequestParam int page,
                                                    @RequestParam int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<OrderDto> ordersPage = userService.findAllUserOrders(id, paging);
        if (ordersPage.getNumber() >= ordersPage.getTotalPages()) {
            throw new NoSuchPageException(ordersPage.getNumber());
        }
        return pagedOrderResourcesAssembler.toModel(ordersPage, orderModelAssembler);
    }

    /**
     * Find the most popular tag of the user.
     *
     * @param id id of the user
     * @return the {@code TagModel} object
     */
    @GetMapping(value = "/{id}/orders/gift_certificates/tags/most_popular")
    public TagModel findUserMostPopularTags(@PathVariable long id) {
        return tagModelAssembler.toModel(userService.findUserMostPopularTag(id));
    }

    /**
     * Handle the {@code EntityNotFoundException}.
     *
     * @param ex the {@code EntityNotFoundException} object
     * @return the {@code CustomError} object
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomError handleUserNotFound(EntityNotFoundException ex) {
        return new CustomError(CustomErrorCode.USER_NOT_FOUND.code,
                localeService.getLocaleMessage(ENTITY_NOT_FOUND_ERROR, ex.getId()));
    }

    /**
     * Handle the {@code EntityNotExistException}.
     *
     * @param ex the {@code EntityNotExistException} object
     * @return the {@code CustomError} object
     */
    @ExceptionHandler(EntityNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomError handleUserNotExist(EntityNotExistException ex) {
        return new CustomError(CustomErrorCode.USER_NOT_EXIST.code,
                localeService.getLocaleMessage(ENTITY_NOT_EXIST_ERROR, ex.getId()));
    }
}
