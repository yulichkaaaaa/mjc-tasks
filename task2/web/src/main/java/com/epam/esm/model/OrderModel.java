package com.epam.esm.model;

import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderModel extends RepresentationModel<OrderModel> {

    private long id;
    private BigDecimal cost;
    private LocalDateTime purchaseTimestamp;
    private UserModel user;
    private GiftCertificateModel giftCertificate;

    /**
     * Construct order object with given id, cost, purchase timestamp,
     * user and gift certificate.
     *
     * @param id                id of the order
     * @param cost              cost of the certificate on the moment of purchase
     * @param purchaseTimestamp date and time of order registration
     * @param user              user that made an order
     * @param giftCertificate   certificate that was ordered
     */
    public OrderModel(long id, BigDecimal cost,
                      LocalDateTime purchaseTimestamp, UserModel user,
                      GiftCertificateModel giftCertificate) {
        this.id = id;
        this.cost = cost;
        this.purchaseTimestamp = purchaseTimestamp;
        this.user = user;
        this.giftCertificate = giftCertificate;
    }

    /**
     * Construct order model.
     */
    public OrderModel() {
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
     * @return the {@code UserModel} object
     */
    public UserModel getUser() {
        return user;
    }

    /**
     * Setter method of the user.
     *
     * @param user the {@code UserModel} object
     */
    public void setUser(UserModel user) {
        this.user = user;
    }

    /**
     * Getter method of the gift certificate.
     *
     * @return the {@code GiftCertificateModel} object
     */
    public GiftCertificateModel getGiftCertificate() {
        return giftCertificate;
    }

    /**
     * Getter method of the gift certificate.
     *
     * @param giftCertificate the {@code GiftCertificateModel} object
     */
    public void setGiftCertificate(GiftCertificateModel giftCertificate) {
        this.giftCertificate = giftCertificate;
    }
}
