package com.epam.esm.exception;

public class EntityNotFoundException extends RuntimeException{

    private long id;

    public EntityNotFoundException(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
