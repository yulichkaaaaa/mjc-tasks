package com.epam.esm.exception;

public class EntityNotExistException extends RuntimeException{

    private long id;

    public EntityNotExistException(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
