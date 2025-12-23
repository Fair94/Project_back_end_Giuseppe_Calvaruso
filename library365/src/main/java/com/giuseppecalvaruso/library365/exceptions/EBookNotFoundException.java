package com.giuseppecalvaruso.library365.exceptions;

import java.util.UUID;

public class EBookNotFoundException extends RuntimeException {

    public EBookNotFoundException(String ISBN) {
        super("Book/Ebook with  " + ISBN + " not found");
    }
}


