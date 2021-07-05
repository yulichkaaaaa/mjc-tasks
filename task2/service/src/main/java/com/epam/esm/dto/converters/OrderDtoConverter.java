package com.epam.esm.dto.converters;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

/**
 * Converter of the order's data transfer object class.
 *
 * @author Shuleiko Yulia
 */
@Component
public class OrderDtoConverter {

    /**
     * Convert order's data transfer object to entity.
     *
     * @param orderDto        the {@code OrderDto} object
     * @param user            the {@code User} object
     * @param giftCertificate the {@code GiftCertificate} object
     * @return the {@code Order} object
     */
    public Order convertToEntity(OrderDto orderDto, User user, GiftCertificate giftCertificate) {
        return new Order(orderDto.getId(),
                user,
                giftCertificate);
    }

    /**
     * Convert order's entity to data transfer object.
     *
     * @param order              the {@code Order} object
     * @param userDto            the {@code UserDto} object
     * @param giftCertificateDto the {@code GiftCertificateDto} object
     * @return the {@code OrderDto} object
     */
    public OrderDto convertToDto(Order order, UserDto userDto, GiftCertificateDto giftCertificateDto) {
        return new OrderDto(order.getId(),
                order.getCost(),
                order.getPurchaseTimestamp(),
                userDto,
                giftCertificateDto);
    }
}
