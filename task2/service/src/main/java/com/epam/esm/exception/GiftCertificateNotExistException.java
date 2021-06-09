package com.epam.esm.exception;

public class GiftCertificateNotExistException extends RuntimeException{

    private long id;

    public GiftCertificateNotExistException(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
