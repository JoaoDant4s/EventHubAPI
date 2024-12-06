package com.imd.web2.user.resources.exceptions;

public class CpfNotValidException extends RuntimeException {
    public CpfNotValidException(String msg) {
        super(msg);
    }
}
