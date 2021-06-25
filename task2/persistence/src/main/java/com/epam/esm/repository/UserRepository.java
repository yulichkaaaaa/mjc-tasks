package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface provides operations with the storage of {@code User} entity.
 *
 * @author Shuleiko Yulia
 */
public interface UserRepository {

    /**
     * Find user by it's id.
     *
     * @param userId id of the user
     * @return the {@code User} object
     */
    Optional<User> findUserById(long userId);

    /**
     * Find all users.
     *
     * @return list of users
     */
    List<User> getAllUsers();

    /**
     * Find all orders of the user with given id.
     *
     * @param userId id of the user
     * @return list of orders
     */
    List<Order> findAllUserOrders(long userId);

    /**
     * Find the most popular tag of the user by user's ordered gift certificates.
     *
     * @param userId id of the user
     * @return the {@code Tag} object
     */
    Optional<Tag> findUserMostPopularTag(long userId);
}
