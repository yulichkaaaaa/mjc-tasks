package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param pageable the {@code Pageable} implementation
     * @return page of users
     */
    Page<UserDto> findAllUsers(Pageable pageable);

    /**
     * Find all user's orders.
     *
     * @param userId   id of the user
     * @param pageable the {@code Pageable} implementation
     * @return page of orders
     */
    Page<OrderDto> findAllUserOrders(long userId, Pageable pageable);

    /**
     * Find the most popular tag of the user.
     *
     * @param userId id of the user
     * @return the {@code TagDto} object
     */
    TagDto findUserMostPopularTag(long userId);
}
