package com.epam.esm.entity;

import java.util.Objects;

/**
 * Tag entity.
 *
 * @author Shuliko Yulia
 */
public class Tag {

    private long id;
    private String name;

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
