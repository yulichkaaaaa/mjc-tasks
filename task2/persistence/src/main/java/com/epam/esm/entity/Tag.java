package com.epam.esm.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * Tag entity.
 *
 * @author Shuliko Yulia
 */
@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<GiftCertificate> giftCertificates;

    /**
     * Construct tag object.
     */
    public Tag() {
    }

    /**
     * Construct tag object with given id and name.
     *
     * @param id   id of the tag
     * @param name name of the tag
     */
    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
        giftCertificates = new HashSet<>();
    }

    /**
     * Getter method of the tag id.
     *
     * @return id of the tag
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method of the tag id.
     *
     * @param id id of the tag
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter method of the tag name.
     *
     * @return name of the tag
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method of the tag name.
     *
     * @param name name of the tag
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for gift certificates.
     *
     * @return set of gift certificates
     */
    public Set<GiftCertificate> getGiftCertificates() {
        return giftCertificates;
    }

    /**
     * Add gift certificates connected to the tag.
     *
     * @param giftCertificate the {@code GiftCertificate} object
     */
    public void addGiftCertificate(GiftCertificate giftCertificate) {
        giftCertificates.add(giftCertificate);
        giftCertificate.getTags().add(this);
    }

    /**
     * Remove gift certificate and tag connection.
     *
     * @param giftCertificate the {@code GiftCertificate} object
     */
    public void removeGiftCertificate(GiftCertificate giftCertificate) {
        giftCertificates.remove(giftCertificate);
        giftCertificate.getTags().remove(this);
    }

    /**
     * Remove all connections between tag and gift certificates.
     */
    public void removeAllGiftCertificates() {
        for (Iterator<GiftCertificate> iterator = giftCertificates.iterator(); iterator.hasNext(); ) {
            GiftCertificate giftCertificate = iterator.next();
            giftCertificate.getTags().remove(this);
            iterator.remove();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return id == tag.id && Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tag{");
        sb.append("tagId=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
