package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.GiftCertificate_;
import com.epam.esm.entity.Tag_;
import com.epam.esm.repository.GiftCertificateRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Implementation of the {@code GiftCertificateRepository} that uses JDBC.
 *
 * @author Shuleiko Yulia
 */
@Repository
public class JpaGiftCertificateRepository implements GiftCertificateRepository {

    private EntityManager entityManager;

    /**
     * Setter method of the {@code EntityManager} object.
     *
     * @param entityManager the {@code EntityManager} object
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long createGiftCertificate(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        entityManager.flush();
        return giftCertificate.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateGiftCertificate(GiftCertificate giftCertificate) {
        entityManager.merge(giftCertificate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteGiftCertificate(long giftCertificateId) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, giftCertificateId);
        //remove all tags
        entityManager.remove(giftCertificate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<GiftCertificate> findGiftCertificateById(long giftCertificateId) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, giftCertificateId);
        return Optional.ofNullable(giftCertificate);
    }

    @Override
    public List<GiftCertificate> findGiftCertificatesByCriteria(String tagName, String name, String description, List<String> sortParams, String sortDirection) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificate = criteriaQuery.from(GiftCertificate.class);
        SetJoin<GiftCertificate, Tag> tag = giftCertificate.join(GiftCertificate_.tags);
        Predicate searchCriteria
                = defineSearchCriteria(criteriaBuilder, giftCertificate, tag, tagName, name, description);
        List<Order> sortOrder = defineSortOrder(criteriaBuilder, giftCertificate, sortParams, sortDirection);
        criteriaQuery
                .select(giftCertificate)
                .where(searchCriteria)
                .orderBy(sortOrder);
        TypedQuery<GiftCertificate> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    private Predicate defineSearchCriteria(CriteriaBuilder criteriaBuilder,
                                           Root<GiftCertificate> giftCertificate,
                                           SetJoin<GiftCertificate, Tag> tag, String tagName,
                                           String name, String description) {
        tagName = Objects.isNull(tagName) ? "" : tagName;
        name = Objects.isNull(name) ? "" : name;
        description = Objects.isNull(description) ? "" : description;
        return criteriaBuilder.and(criteriaBuilder.like(tag.get(Tag_.name), "%" + tagName + "%"),
                criteriaBuilder.like(giftCertificate.get(GiftCertificate_.name), "%" + name + "%"),
                criteriaBuilder.like(giftCertificate.get(GiftCertificate_.description), "%" + description + "%"));
    }

    private List<Order> defineSortOrder(CriteriaBuilder criteriaBuilder, Root<GiftCertificate> giftCertificate,
                                        List<String> sortCriteria, String sortDirection) {
        List<Order> sortOrder = new ArrayList<>();
        for (String criteria : sortCriteria) {
            if(criteria.equals("name")) {
                Path<String> path = giftCertificate.get(GiftCertificate_.name);
                sortOrder.add(sortDirection.equals("desc") ? criteriaBuilder.desc(path) : criteriaBuilder.asc(path));
            } else if (criteria.equals("create_date")){
                Path<LocalDateTime> path = giftCertificate.get(GiftCertificate_.createDate);
                sortOrder.add(sortDirection.equals("desc") ? criteriaBuilder.desc(path) : criteriaBuilder.asc(path));
            }
        }
        return sortOrder;
    }
}
