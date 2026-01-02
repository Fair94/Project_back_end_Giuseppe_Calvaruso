package com.giuseppecalvaruso.library365.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GoogleNotFoundException extends RuntimeException {

    public GoogleNotFoundException(String message) {
        super("Error");
    }
}
