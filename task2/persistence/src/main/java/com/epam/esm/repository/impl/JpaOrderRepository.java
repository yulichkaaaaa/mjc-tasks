package com.epam.esm.repository.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Implementation of the {@code OrderRepository} that uses JPA.
 *
 * @author Shuleiko Yulia
 */
@Repository
public class JpaOrderRepository implements OrderRepository {

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
    public long createOrder(Order order) {
        entityManager.persist(order);
        entityManager.flush();
        return order.getId();
    }
}
