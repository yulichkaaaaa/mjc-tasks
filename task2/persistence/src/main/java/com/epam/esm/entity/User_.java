package com.epam.esm.entity;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel of user entity.
 *
 * @author Yulia Shuleiko
 */
@StaticMetamodel(User.class)
public class User_ {

    public static volatile SingularAttribute<User, Long> id;
    public static volatile SingularAttribute<User, String> name;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> passwordHash;
    public static volatile SetAttribute<User, Order> orders;
}
