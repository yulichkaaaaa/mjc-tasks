package com.epam.esm.repository.impl;

import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository {

    private EntityManager entityManager;
    private static final String JPQL_SELECT_ALL_USERS = "Select u from User u";

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> findUserById(long userId) {
        return Optional.ofNullable(entityManager.find(User.class, userId));
    }

    @Override
    public List<User> getAllUsers() {
        Query query = entityManager.createQuery(JPQL_SELECT_ALL_USERS);
        return (List<User>) query.getResultList();
    }
}
