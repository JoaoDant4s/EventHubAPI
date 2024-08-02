package com.imd.web2.user.resources.exceptions;

public class CreditCardNotValidException extends RuntimeException {
    public CreditCardNotValidException(String msg) {
        super(msg);
    }
}
