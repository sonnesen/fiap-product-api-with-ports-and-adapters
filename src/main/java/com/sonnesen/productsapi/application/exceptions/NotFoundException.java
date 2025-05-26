package com.sonnesen.productsapi.application.exceptions;

public class NotFoundException extends DomainException {

    public NotFoundException(String message) {
        super(message);
    }

}