package com.epam.esm.exception;

public class GiftCertificateNotFoundException extends RuntimeException{

    private long id;

    public GiftCertificateNotFoundException(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
