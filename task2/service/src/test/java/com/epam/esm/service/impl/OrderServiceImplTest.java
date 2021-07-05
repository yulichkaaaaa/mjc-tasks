package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.User;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    private OrderService orderService;
    private GiftCertificate giftCertificate;
    private User user;

    @BeforeAll
    public void init() {
        MockitoAnnotations.openMocks(this);
        giftCertificate = new GiftCertificate(1, "present", "some information",
                new BigDecimal("12.32"), 12,
                LocalDateTime.of(2019, Month.APRIL, 15, 0, 0),
                LocalDateTime.now());
        user = new User(1, "yulia", "yulia@gmail.com", "12345678");
        orderService = new OrderServiceImpl(userRepository, orderRepository, giftCertificateRepository);
    }

    @Test
    public void makeOrderTest(){
        long userId = 1;
        long giftCertificateId = 1;
        Mockito.when(giftCertificateRepository.findGiftCertificateById(giftCertificateId))
                .thenReturn(Optional.of(giftCertificate));
        Mockito.when(userRepository.findUserById(userId))
                .thenReturn(Optional.of(user));
        orderService.makeOrder(userId, giftCertificateId);
        Mockito.verify(orderRepository, Mockito.times(1))
                .createOrder(Mockito.any());
    }
}
