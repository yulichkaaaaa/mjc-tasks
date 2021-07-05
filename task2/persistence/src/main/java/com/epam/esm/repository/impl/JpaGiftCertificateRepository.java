package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.Tag_;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificate_;
import com.epam.esm.repository.GiftCertificateRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Order;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Implementation of the {@code GiftCertificateRepository} that uses JPA.
 *
 * @author Shuleiko Yulia
 */
@Repository
public class JpaGiftCertificateRepository implements GiftCertificateRepository {

    private EntityManager entityManager;
    private static final String SORT_DIRECTION_DESC = "desc";

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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificate> findGiftCertificatesByCriteria(List<String> tagNames, String name, String description,
                                                                List<String> sortParams, String sortDirection,
                                                                int pageNumber, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificate = criteriaQuery.from(GiftCertificate.class);
        SetJoin<GiftCertificate, Tag> tag = giftCertificate.join(GiftCertificate_.tags);
        Predicate[] searchCriteria
                = defineSearchCriteria(criteriaBuilder, giftCertificate, tag, tagNames, name, description);
        List<Order> sortOrder = defineSortOrder(criteriaBuilder, giftCertificate, sortParams, sortDirection);
        criteriaQuery
                .select(giftCertificate)
                .where(searchCriteria)
                .groupBy(giftCertificate.get(GiftCertificate_.id))
                .having(criteriaBuilder.count(giftCertificate).in(tagNames.size()))
                .orderBy(sortOrder);
        TypedQuery<GiftCertificate> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.setMaxResults(pageSize).setFirstResult(pageNumber * pageSize).getResultList();
    }

    /**
     * Define general criteria of search.
     *
     * @param name        name of the gift certificate
     * @param description description of the gift certificate
     * @return the {@code Predicate} object
     */
    private Predicate[] defineSearchCriteria(CriteriaBuilder criteriaBuilder,
                                             Root<GiftCertificate> giftCertificate,
                                             SetJoin<GiftCertificate, Tag> tag, List<String> tagNames,
                                             String name, String description) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(giftCertificate.get(GiftCertificate_.isDeleted), false));
        predicates.add(tag.get(Tag_.name).in(tagNames));
        if (Objects.nonNull(name)) {
            predicates.add(criteriaBuilder
                    .like(giftCertificate.get(GiftCertificate_.name), "%" + name + "%"));
        }
        if (Objects.nonNull(description)) {
            predicates.add(criteriaBuilder
                    .like(giftCertificate.get(GiftCertificate_.description), "%" + description + "%"));
        }
        return predicates.toArray(new Predicate[]{});
    }

    /**
     * Define general criteria of the sort.
     *
     * @param sortCriteria  list of criteria of sorting
     * @param sortDirection direction of sort (asc or desc)
     * @return list of orders
     */
    private List<Order> defineSortOrder(CriteriaBuilder criteriaBuilder, Root<GiftCertificate> giftCertificate,
                                        List<String> sortCriteria, String sortDirection) {
        List<Order> sortOrder = new ArrayList<>();
        for (String criteria : sortCriteria) {
            Path<String> path = giftCertificate.get(criteria);
            sortOrder.add(sortDirection.equals(SORT_DIRECTION_DESC) ? criteriaBuilder.desc(path) : criteriaBuilder.asc(path));
        }
        return sortOrder;
    }
}
