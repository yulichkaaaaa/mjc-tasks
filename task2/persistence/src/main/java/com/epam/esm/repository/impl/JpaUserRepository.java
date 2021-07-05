package com.epam.esm.repository.impl;

import com.epam.esm.entity.User;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User_;
import com.epam.esm.entity.Order_;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.Tag_;
import com.epam.esm.entity.GiftCertificate_;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Join;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@code UserRepository} that uses JPA.
 *
 * @author Shuleiko Yulia
 */
@Repository
public class JpaUserRepository implements UserRepository {

    private static final String JPQL_SELECT_ALL_USERS = "Select u from User u";
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
    public Optional<User> findUserById(long userId) {
        return Optional.ofNullable(entityManager.find(User.class, userId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAllUsers(int pageNumber, int pageSize) {
        Query query = entityManager.createQuery(JPQL_SELECT_ALL_USERS);
        return (List<User>) query.setMaxResults(pageSize).setFirstResult(pageNumber * pageSize).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> findAllUserOrders(long userId, int pageNumber, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> order = criteriaQuery.from(Order.class);
        Join<Order, User> user = order.join(Order_.user);
        criteriaQuery.select(order).where(criteriaBuilder.equal(user.get(User_.id), userId));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.setFirstResult(pageNumber * pageSize).setMaxResults(pageSize).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Tag> findUserMostPopularTag(long userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tag = criteriaQuery.from(Tag.class);
        Join<GiftCertificate, Order> order = tag
                .join(Tag_.giftCertificates)
                .join(GiftCertificate_.orders);
        Join<Order, User> user = order.join(Order_.user);
        criteriaQuery
                .select(tag)
                .where(criteriaBuilder.equal(user.get(User_.id), userId))
                .groupBy(tag.get(Tag_.name))
                .orderBy(criteriaBuilder.desc(criteriaBuilder.sum(order.get(Order_.cost))));
        TypedQuery<Tag> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Tag> tags = typedQuery.setMaxResults(1).getResultList();
        return tags.isEmpty() ? Optional.empty() : Optional.of(tags.get(0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countUserOrders(long userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Order> order = criteriaQuery.from(Order.class);
        Join<Order, User> user = order.join(Order_.user);
        criteriaQuery.select(criteriaBuilder.count(order))
                .where(criteriaBuilder.equal(user.get(User_.id), userId));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countUsers() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(User.class)));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}
