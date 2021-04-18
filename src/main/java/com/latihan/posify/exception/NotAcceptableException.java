package com.latihan.posify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NotAcceptableException extends RuntimeException {
    private static final long serialVersionUID = -2491666804368831276L;

    public NotAcceptableException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }
}
