package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.Tag_;
import com.epam.esm.repository.TagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;
import java.util.List;

/**
 * Implementation of the {@code TagRepository} that uses JPA.
 *
 * @author Shuleiko Yulia
 */
@Repository
public class JpaTagRepository implements TagRepository {

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
    public long createTag(Tag tag) {
        entityManager.persist(tag);
        entityManager.flush();
        return tag.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteTag(long tagId) {
        Tag tag = entityManager.find(Tag.class, tagId);
        tag.removeAllGiftCertificates();
        entityManager.remove(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Tag> findTagById(long tagId) {
        Tag tag = entityManager.find(Tag.class, tagId);
        return Optional.ofNullable(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Tag> findTagByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tag = criteriaQuery.from(Tag.class);
        criteriaQuery.where(criteriaBuilder.equal(tag.get(Tag_.name), name));
        TypedQuery<Tag> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Tag> tags = typedQuery.getResultList();
        return tags.isEmpty() ? Optional.empty() : Optional.of(tags.get(0));
    }
}
