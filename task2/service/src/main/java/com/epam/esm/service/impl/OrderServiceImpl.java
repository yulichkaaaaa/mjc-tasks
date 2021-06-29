package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementation of the {@code OrderService}.
 *
 * @author Shuleiko Yulia
 */
@Service
public class OrderServiceImpl implements OrderService {

    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private GiftCertificateRepository giftCertificateRepository;

    /**
     * Construct order service with all necessary dependencies.
     */
    public OrderServiceImpl(UserRepository userRepository,
                            OrderRepository orderRepository,
                            GiftCertificateRepository giftCertificateRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.giftCertificateRepository = giftCertificateRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void makeOrder(long userId, long giftCertificateId) {
        GiftCertificate giftCertificate = findGiftCertificateById(giftCertificateId);
        User user = findUserById(userId);
        Order order = new Order(user, giftCertificate);
        orderRepository.createOrder(order);
    }

    /**
     * Find user by it's id.
     *
     * @param userId id of the user
     * @return the {@code User} object
     */
    private User findUserById(long userId) {
        Optional<User> userOptional = userRepository.findUserById(userId);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException(userId);
        }
        return userOptional.get();
    }

    /**
     * Find gift certificate by it's id.
     *
     * @param giftCertificateId id of the gift certificate
     * @return the {@code GiftCertificate} object
     */
    private GiftCertificate findGiftCertificateById(long giftCertificateId) {
        Optional<GiftCertificate> giftCertificateOptional
                = giftCertificateRepository.findGiftCertificateById(giftCertificateId);
        if (giftCertificateOptional.isEmpty()) {
            throw new EntityNotFoundException(giftCertificateId);
        }
        return giftCertificateOptional.get();
    }
}
