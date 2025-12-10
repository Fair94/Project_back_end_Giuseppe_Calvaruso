package com.giuseppecalvaruso.library365.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrintedBookService {
    @Autowired
    private UserService userService;
}
