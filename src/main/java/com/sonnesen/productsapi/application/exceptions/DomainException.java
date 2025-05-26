package com.sonnesen.productsapi.application.exceptions;

public class DomainException extends RuntimeException {

    public DomainException(String message) {
        this(message, null);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause, true, false);
    }

}
