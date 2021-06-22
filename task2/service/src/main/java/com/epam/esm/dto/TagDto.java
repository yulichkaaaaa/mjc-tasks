package com.epam.esm.dto;

import java.util.Objects;

/**
 * Data transfer object of the tag.
 *
 * @author Shuleiko Yulia.
 */
public class TagDto {

    private long id;
    private String name;

    /**
     * Construct object with given name.
     *
     * @param name name of the tag
     */
    public TagDto(String name) {
        this.name = name;
    }

    /**
     * Construct object with given id and name.
     *
     * @param id   id of the tag
     * @param name name of the tag
     */
    public TagDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TagDto() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagDto tagDto = (TagDto) o;
        return id == tagDto.id && Objects.equals(name, tagDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
