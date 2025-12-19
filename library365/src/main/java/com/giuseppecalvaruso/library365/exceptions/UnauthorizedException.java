package com.giuseppecalvaruso.library365.exceptions;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException (String message){
        super(message);
    }
}
