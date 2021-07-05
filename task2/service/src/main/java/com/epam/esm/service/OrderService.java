package com.epam.esm.service;

/**
 * Interface provides operations with {@code OrderDto} object.
 *
 * @author Shuleiko Yulia
 */
public interface OrderService {

    /**
     * Make new order.
     *
     * @param userId            id of the user
     * @param giftCertificateId id of the gift certificate
     */
    long makeOrder(long userId, long giftCertificateId);
}
