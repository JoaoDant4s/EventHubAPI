package com.imd.web2.auth.resources.exceptions;

public class CpfNotValidException extends RuntimeException {
    public CpfNotValidException(String msg) {
        super(msg);
    }
}
