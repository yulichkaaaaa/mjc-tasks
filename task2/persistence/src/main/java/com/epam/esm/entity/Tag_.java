package com.epam.esm.entity;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel of tag entity.
 *
 * @author Yulia Shuleiko
 */
@StaticMetamodel(Tag.class)
public class Tag_ {

    public static volatile SingularAttribute<Tag, Long> id;
    public static volatile SingularAttribute<Tag, String> name;
    public static volatile SetAttribute<Tag, GiftCertificate> giftCertificates;
}
