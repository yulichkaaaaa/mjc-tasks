package com.epam.esm.repository;

import com.epam.esm.entity.Order;

/**
 * Interface provides operations with the storage of {@code Order} entity.
 *
 * @author Shuleiko Yulia
 */
public interface OrderRepository {

    /**
     * Create new order.
     *
     * @param order the {@code Order} object
     * @return id of the order
     */
    long createOrder(Order order);
}
