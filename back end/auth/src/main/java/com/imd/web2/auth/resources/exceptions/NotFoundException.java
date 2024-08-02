package com.imd.web2.auth.resources.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) {
        super(msg);
    }
}