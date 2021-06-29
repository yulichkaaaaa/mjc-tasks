package com.epam.esm.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.PrePersist;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Order entity.
 *
 * @author Shuleiko Yulia
 */
@Entity
@Table(name = "\"order\"")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private BigDecimal cost;

    @Column(name = "purchase_timestamp")
    private LocalDateTime purchaseTimestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "gift_certificate_id", nullable = false)
    private GiftCertificate giftCertificate;

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
    public Order(long id, BigDecimal cost, LocalDateTime purchaseTimestamp,
                 User user, GiftCertificate giftCertificate) {
        this.id = id;
        this.cost = cost;
        this.purchaseTimestamp = purchaseTimestamp;
        this.user = user;
        this.giftCertificate = giftCertificate;
    }

    /**
     * Construct order object with given user and gift certificate.
     *
     * @param user              user that made an order
     * @param giftCertificate   certificate that was ordered
     */
    public Order(User user, GiftCertificate giftCertificate) {
        this.user = user;
        this.giftCertificate = giftCertificate;
    }

    /**
     * Construct order object.
     */
    public Order() {
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
    public User getUser() {
        return user;
    }

    /**
     * Setter method of the user.
     *
     * @param user user that made an order
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter method of the gift certificate.
     *
     * @return certificate that was ordered
     */
    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    /**
     * Setter method of the gift certificate.
     *
     * @param giftCertificate certificate that was ordered
     */
    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    /**
     * Auditing persist.
     */
    @PrePersist
    public void onPrePersist() {
        cost = giftCertificate.getPrice();
        purchaseTimestamp = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Objects.equals(cost, order.cost)
                && Objects.equals(purchaseTimestamp, order.purchaseTimestamp)
                && Objects.equals(user, order.user)
                && Objects.equals(giftCertificate, order.giftCertificate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cost, purchaseTimestamp, user, giftCertificate);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(id);
        sb.append(", cost=").append(cost);
        sb.append(", purchaseTimestamp=").append(purchaseTimestamp);
        sb.append(", user=").append(user);
        sb.append(", giftCertificate=").append(giftCertificate);
        sb.append('}');
        return sb.toString();
    }
}
