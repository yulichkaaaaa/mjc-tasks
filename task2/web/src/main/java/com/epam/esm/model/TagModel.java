package com.epam.esm.model;

import org.springframework.hateoas.RepresentationModel;

/**
 * Representational model of tag.
 *
 * @author Shuleiko Yulia
 */
public class TagModel extends RepresentationModel<TagModel> {

    private long id;
    private String name;

    /**
     * Construct object with given id and name.
     *
     * @param id   id of the tag
     * @param name name of the tag
     */
    public TagModel(long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Construct tag object.
     */
    public TagModel() {
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
}
