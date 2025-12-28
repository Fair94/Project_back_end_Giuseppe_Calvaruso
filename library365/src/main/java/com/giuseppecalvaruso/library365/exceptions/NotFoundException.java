package com.giuseppecalvaruso.library365.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(UUID user_id) {
        super("User with id " + user_id + " not found");
    }
}
