package com.imd.web2.auth.resources.exceptions;

public class CreditCardNotValidException extends RuntimeException {
    public CreditCardNotValidException(String msg) {
        super(msg);
    }
}
