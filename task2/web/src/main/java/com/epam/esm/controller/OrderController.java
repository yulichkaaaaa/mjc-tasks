package com.epam.esm.controller;

import com.epam.esm.service.LocaleService;
import com.epam.esm.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Rest controller that processes requests with the orders.
 *
 * @author Shuleiko Yulia
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;
    private LocaleService localeService;

    /**
     * Construct controller with all necessary dependencies.
     */
    public OrderController(OrderService orderService, LocaleService localeService) {
        this.orderService = orderService;
        this.localeService = localeService;
    }

    /**
     * Make order with user id and gift certificate id.
     *
     * @param userId id of the user
     * @param giftCertificateId id of the gift certificate
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void makeOrder(@RequestParam long userId,
                          @RequestParam long giftCertificateId) {
        orderService.makeOrder(userId, giftCertificateId);
    }
}
