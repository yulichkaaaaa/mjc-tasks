package com.epam.esm.model;

import org.springframework.hateoas.RepresentationModel;

public class UserModel extends RepresentationModel<UserModel> {

    private long id;
    private String name;
    private String email;

    /**
     * Construct user object with given id, name and email.
     *
     * @param id    id of the user
     * @param name  name of the user
     * @param email email of the user
     */
    public UserModel(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Construct user model.
     */
    public UserModel() {
    }

    /**
     * Getter method of the user's id.
     *
     * @return id of the user
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method of the user's id.
     *
     * @param id id of the user
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter method of the name.
     *
     * @return name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method of the name.
     *
     * @param name name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method of the email.
     *
     * @return email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method of the email.
     *
     * @param email email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
