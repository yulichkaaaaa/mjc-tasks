package com.epam.esm.dto;

import java.util.Objects;

/**
 * Data transfer object of the user.
 *
 * @author Shuleiko Yulia
 */
public class UserDto {

    private long id;
    private String name;
    private String email;
    private String password;

    /**
     * Construct user object with given id, name, email and password.
     *
     * @param id       id of the user
     * @param name     name of the user
     * @param email    email of the user
     * @param password password
     */
    public UserDto(long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Construct user object with given name, email and password.
     *
     * @param name     name of the user
     * @param email    email of the user
     * @param password password
     */
    public UserDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Construct user object.
     */
    public UserDto() {
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

    /**
     * Getter method of the password.
     *
     * @return hash of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter method of the password.
     *
     * @param password hash of password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return id == userDto.id && Objects.equals(name, userDto.name)
                && Objects.equals(email, userDto.email)
                && Objects.equals(password, userDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDto{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
