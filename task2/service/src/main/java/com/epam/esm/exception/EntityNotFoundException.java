package com.epam.esm.exception;

/**
 * Is thrown when attempt to find entity that isn't in storage.
 *
 * @author Shuleiko Yulia
 */
public class EntityNotFoundException extends RuntimeException {

    private long id;

    /**
     * Constructs exception with given id of the entity.
     *
     * @param id id of the entity
     */
    public EntityNotFoundException(long id) {
        this.id = id;
    }

    /**
     * Getter method of the id of the entity
     *
     * @return id of the entity
     */
    public long getId() {
        return id;
    }
}
