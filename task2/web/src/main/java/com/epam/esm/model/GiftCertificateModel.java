package com.epam.esm.model;

import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class GiftCertificateModel extends RepresentationModel<GiftCertificateModel> {

    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private Set<TagModel> tags;

    /**
     * Construct gift certificate object with given id, name, description,
     * price, duration, create date, last update date, tags.
     *
     * @param id             id of the gift certificate
     * @param name           name of the gift certificate
     * @param description    description of the gift certificate
     * @param price          price of the gift certificate
     * @param duration       duration (in days) of the gift certificate validity
     * @param lastUpdateDate date when the gift certificate was updated last time
     * @param tags           set of tags of gift certificate
     */
    public GiftCertificateModel(long id, String name, String description,
                                BigDecimal price, Integer duration, LocalDateTime createDate,
                                LocalDateTime lastUpdateDate, Set<TagModel> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = new HashSet<>();
        this.tags.addAll(tags);
    }

    /**
     * Construct gift certificate data transfer object.
     */
    public GiftCertificateModel() {
        tags = new HashSet<>();
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
    public Set<TagModel> getTags() {
        return new HashSet<>(tags);
    }
}
