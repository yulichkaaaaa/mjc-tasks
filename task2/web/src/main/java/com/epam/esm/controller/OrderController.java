package com.epam.esm.controller;

import com.epam.esm.service.LocaleService;
import com.epam.esm.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller that processes requests with the orders.
 *
 * @author Shuleiko Yulia
 */
@RestController
@RequestMapping("orders")
public class OrderController {

    private OrderService orderService;
    private LocaleService localeService;

    public OrderController(OrderService orderService, LocaleService localeService) {
        this.orderService = orderService;
        this.localeService = localeService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void makeOrder(@RequestParam long userId,
                          @RequestParam long giftCertificateId) {
        orderService.makeOrder(userId, giftCertificateId);
    }
}
