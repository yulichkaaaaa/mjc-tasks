package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;

import java.util.List;

/**
 * Interface provides operations with {@code UserDto} object.
 *
 * @author Shuleiko Yulia
 */
public interface UserService {

    /**
     * Find user by id.
     *
     * @param userId id of the user
     * @return the {@code UserDto} object
     */
    UserDto findUserById(long userId);

    /**
     * Find all users.
     *
     * @return list of users
     */
    List<UserDto> findAllUsers();

    /**
     * Find all user's orders.
     *
     * @param userId id of the user
     * @return list of orders
     */
    List<OrderDto> findAllUserOrders(long userId);

    /**
     * Find the most popular tag of the user.
     *
     * @param userId id of the user
     * @return the {@code TagDto} object
     */
    TagDto findUserMostPopularTag(long userId);
}
