package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.error.CustomError;
import com.epam.esm.error.CustomErrorCode;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.LocaleService;
import com.epam.esm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Rest controller that processes requests with the users.
 *
 * @author Shuleiko Yulia
 */
@RestController
@RequestMapping("users")
public class UserController {

    private static final String ENTITY_NOT_FOUND_ERROR = "entity_not_found";
    private UserService userService;
    private LocaleService localeService;

    public UserController(UserService userService, LocaleService localeService) {
        this.userService = userService;
        this.localeService = localeService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserDto findUserById(@PathVariable long id) {
        return userService.findUserById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserDto> findAllUsers() {
        return userService.findAllUsers();
    }

    @RequestMapping(value = "/{id}/orders", method = RequestMethod.GET)
    public List<OrderDto> findAllUserOrders(@PathVariable long id) {
        return userService.findAllUserOrders(id);
    }

    @RequestMapping(value = "/{id}/orders/gift_certificates/tags", method = RequestMethod.GET)
    public List<TagDto> findUserTags(@PathVariable long id,
                                     @RequestParam(required = false) String popular) {
        List<TagDto> tags = new ArrayList<>();
        if (Objects.nonNull(popular)){
            tags.add(userService.findUserMostPopularTag(id));
        }
        return tags;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomError handleUserNotFound(EntityNotFoundException ex) {
        return new CustomError(CustomErrorCode.USER_NOT_FOUND.code,
                localeService.getLocaleMessage(ENTITY_NOT_FOUND_ERROR, ex.getId()));
    }
}
