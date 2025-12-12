package com.giuseppecalvaruso.library365.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {

    public NotFoundException(UUID user_id) {
        super("User with id " + user_id + " not found");
    }
}
