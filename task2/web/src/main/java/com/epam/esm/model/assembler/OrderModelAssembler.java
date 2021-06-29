package com.epam.esm.model.assembler;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.model.OrderModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Assemble representational model of order from data transfer object.
 *
 * @author Shuleiko Yulia
 */
@Component
public class OrderModelAssembler extends RepresentationModelAssemblerSupport<OrderDto, OrderModel> {

    private GiftCertificateModelAssembler giftCertificateModelAssembler;
    private UserModelAssembler userModelAssembler;

    /**
     * Creates a new {@link RepresentationModelAssemblerSupport} using
     * the given controller class and resource type.
     */
    public OrderModelAssembler(GiftCertificateModelAssembler giftCertificateModelAssembler,
                               UserModelAssembler userModelAssembler) {
        super(OrderController.class, OrderModel.class);
        this.giftCertificateModelAssembler = giftCertificateModelAssembler;
        this.userModelAssembler = userModelAssembler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderModel toModel(OrderDto orderDto) {
        return createResource(orderDto);
    }

    /**
     * Create the {@code OrderModel} object from data transfer object.
     *
     * @param orderDto the {@code OrderDto} object
     * @return the {@code OrderModel} object
     */
    private OrderModel createResource(OrderDto orderDto) {
        return new OrderModel(orderDto.getId(),
                orderDto.getCost(),
                orderDto.getPurchaseTimestamp(),
                userModelAssembler.toModel(orderDto.getUserDto()),
                giftCertificateModelAssembler.toModel(orderDto.getGiftCertificateDto()));
    }
}
