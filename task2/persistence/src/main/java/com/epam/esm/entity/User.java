package com.epam.esm.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;

/**
 * User entity.
 *
 * @author Shuleiko Yulia
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String passwordHash;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    /**
     * Construct user object with given id, name, email and password hash.
     *
     * @param id           id of the user
     * @param name         name of the user
     * @param email        email of the user
     * @param passwordHash hash of password
     */
    public User(long id, String name, String email, String passwordHash) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    /**
     * Construct user object with given name, email and password hash.
     *
     * @param name         name of the user
     * @param email        email of the user
     * @param passwordHash hash of password
     */
    public User(String name, String email, String passwordHash) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    /**
     * Construct user object.
     */
    public User() {
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
     * Getter method of the password hash.
     *
     * @return hash of password
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Setter method of the password hash.
     *
     * @param passwordHash hash of password
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name)
                && Objects.equals(email, user.email)
                && Objects.equals(passwordHash, user.passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, passwordHash);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", passwordHash='").append(passwordHash).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
