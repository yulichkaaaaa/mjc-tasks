package com.epam.esm.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data transfer object of the order.
 *
 * @author Shuleiko Yulia
 */
public class OrderDto {

    private long id;
    private BigDecimal cost;
    private LocalDateTime purchaseTimestamp;
    private UserDto userDto;
    private GiftCertificateDto giftCertificateDto;

    /**
     * Construct order object with given id, cost, purchase timestamp,
     * user and gift certificate.
     *
     * @param id                 id of the order
     * @param cost               cost of the certificate on the moment of purchase
     * @param purchaseTimestamp  date and time of order registration
     * @param userDto            user that made an order
     * @param giftCertificateDto certificate that was ordered
     */
    public OrderDto(long id, BigDecimal cost, LocalDateTime purchaseTimestamp,
                    UserDto userDto, GiftCertificateDto giftCertificateDto) {
        this.id = id;
        this.cost = cost;
        this.purchaseTimestamp = purchaseTimestamp;
        this.userDto = userDto;
        this.giftCertificateDto = giftCertificateDto;
    }

    /**
     * Construct order object with given cost, purchase timestamp,
     * user and gift certificate.
     *
     * @param cost               cost of the certificate on the moment of purchase
     * @param purchaseTimestamp  date and time of order registration
     * @param userDto            user that made an order
     * @param giftCertificateDto certificate that was ordered
     */
    public OrderDto(BigDecimal cost, LocalDateTime purchaseTimestamp,
                    UserDto userDto, GiftCertificateDto giftCertificateDto) {
        this.cost = cost;
        this.purchaseTimestamp = purchaseTimestamp;
        this.userDto = userDto;
        this.giftCertificateDto = giftCertificateDto;
    }

    /**
     * Construct order object.
     */
    public OrderDto() {
    }

    /**
     * Getter method of the order id.
     *
     * @return id of the order
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method of the order id.
     *
     * @param id id of the order
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter method of the cost.
     *
     * @return cost of the certificate on the moment of purchase
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * Setter method of the cost.
     *
     * @param cost cost of the certificate on the moment of purchase
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * Getter method of the purchase timestamp.
     *
     * @return date and time of order registration
     */
    public LocalDateTime getPurchaseTimestamp() {
        return purchaseTimestamp;
    }

    /**
     * Setter method of the purchase timestamp.
     *
     * @param purchaseTimestamp date and time of order registration
     */
    public void setPurchaseTimestamp(LocalDateTime purchaseTimestamp) {
        this.purchaseTimestamp = purchaseTimestamp;
    }

    /**
     * Getter method of the user.
     *
     * @return user that made an order
     */
    public UserDto getUserDto() {
        return userDto;
    }

    /**
     * Setter method of the user.
     *
     * @param userDto user that made an order
     */
    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    /**
     * Getter method of the gift certificate.
     *
     * @return certificate that was ordered
     */
    public GiftCertificateDto getGiftCertificateDto() {
        return giftCertificateDto;
    }

    /**
     * Setter method of the gift certificate.
     *
     * @param giftCertificateDto certificate that was ordered
     */
    public void setGiftCertificateDto(GiftCertificateDto giftCertificateDto) {
        this.giftCertificateDto = giftCertificateDto;
    }
}
