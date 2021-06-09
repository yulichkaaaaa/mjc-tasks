package com.epam.esm.exception;

public class GiftCertificateAlreadyExistsException extends RuntimeException{

    private long id;

    public GiftCertificateAlreadyExistsException(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
