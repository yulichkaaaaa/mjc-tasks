package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.error.CustomError;
import com.epam.esm.error.CustomErrorCode;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.LocaleService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private static final String ENTITY_NOT_FOUND_ERROR = "entity_not_found";
    private UserService userService;
    private LocaleService localeService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setLocaleService(LocaleService localeService) {
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

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomError handleUserNotFound(EntityNotFoundException ex) {
        return new CustomError(CustomErrorCode.USER_NOT_FOUND.code,
                localeService.getLocaleMessage(ENTITY_NOT_FOUND_ERROR, ex.getId()));
    }
}
