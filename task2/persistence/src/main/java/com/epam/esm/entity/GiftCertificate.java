package com.epam.esm.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Gift certificate entity.
 *
 * @author Shuleiko Yulia
 */
@Entity
@Table(name = "gift_certificate")
public class GiftCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private BigDecimal price;

    @Column
    private Integer duration;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @OneToMany(mappedBy = "giftCertificate")
    private Set<Order> orders;

    @ManyToMany
    @JoinTable(
            name = "tag_m2m_gift_certificate",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    /**
     * Construct gift certificate object with given id, name, description,
     * price, duration, createDate, lastUpdateDate.
     *
     * @param id             id of the gift certificate
     * @param name           name of the gift certificate
     * @param description    description of the gift certificate
     * @param price          price of the gift certificate
     * @param duration       duration (in days) of the gift certificate validity
     * @param createDate     date when the gift certificate was created
     * @param lastUpdateDate date when the gift certificate was updated last time
     */
    public GiftCertificate(long id, String name,
                           String description, BigDecimal price,
                           Integer duration, LocalDateTime createDate,
                           LocalDateTime lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        tags = new HashSet<>();
    }

    /**
     * Construct gift certificate object with given name, description,
     * price, duration, createDate, lastUpdateDate.
     *
     * @param name           name of the gift certificate
     * @param description    description of the gift certificate
     * @param price          price of the gift certificate
     * @param duration       duration (in days) of the gift certificate validity
     * @param createDate     date when the gift certificate was created
     * @param lastUpdateDate date when the gift certificate was updated last time
     */
    public GiftCertificate(String name, String description,
                           BigDecimal price, Integer duration,
                           LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        tags = new HashSet<>();
    }

    /**
     * Construct gift certificate object.
     */
    public GiftCertificate() {
    }

    /**
     * Getter method of the gift certificate id.
     *
     * @return id of the gift certificate
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method of the gift certificate id.
     *
     * @param id id of the gift certificate
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter method of the gift certificate name.
     *
     * @return name of the gift certificate
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method of the gift certificate name.
     *
     * @param name name of the gift certificate
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method of the gift certificate description.
     *
     * @return description of the gift certificate
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method of the gift certificate description.
     *
     * @param description description of the gift certificate
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter method of the gift certificate price.
     *
     * @return price of the gift certificate
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Setter method of the gift certificate price.
     *
     * @param price price of the gift certificate
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Getter method of the duration.
     *
     * @return duration (in days) of the gift certificate validity
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Setter method of the duration.
     *
     * @param duration duration (in days) of the gift certificate validity
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Getter method of create date.
     *
     * @return date when the gift certificate was created
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Setter method of create date.
     *
     * @param createDate date when the gift certificate was created
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Getter method of last update date.
     *
     * @return date when the gift certificate was updated last time
     */
    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * Setter method of last update date.
     *
     * @param lastUpdateDate date when the gift certificate was updated last time
     */
    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * Getter method of tags.
     *
     * @return set of tags
     */
    public Set<Tag> getTags() {
        return tags;
    }

    /**
     * Add tag to set.
     *
     * @param tag the {@code Tag} object
     */
    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getGiftCertificates().add(this);
    }

    /**
     * Remove tag from set.
     *
     * @param tag the {@code Tag} object
     */
    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getGiftCertificates().remove(this);
    }

    /**
     * Auditing persist.
     */
    @PrePersist
    public void onPrePersist() {
        createDate = LocalDateTime.now();
    }

    /**
     * Auditing update.
     */
    @PreUpdate
    public void onPreUpdate() {
        lastUpdateDate = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificate that = (GiftCertificate) o;
        return id == that.id && duration == that.duration
                && Objects.equals(name, that.name) && Objects.equals(description, that.description)
                && Objects.equals(price, that.price) && Objects.equals(createDate, that.createDate)
                && Objects.equals(lastUpdateDate, that.lastUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, duration, createDate, lastUpdateDate);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GiftCertificate{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", price=").append(price);
        sb.append(", duration=").append(duration);
        sb.append(", createDate=").append(createDate);
        sb.append(", lastUpdateDate=").append(lastUpdateDate);
        sb.append('}');
        return sb.toString();
    }
}
