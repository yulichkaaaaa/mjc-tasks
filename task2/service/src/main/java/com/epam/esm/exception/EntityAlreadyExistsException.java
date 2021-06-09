package com.epam.esm.exception;

public class EntityAlreadyExistsException extends RuntimeException{

    private long id;

    public EntityAlreadyExistsException(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
