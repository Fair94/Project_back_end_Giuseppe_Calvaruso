package com.giuseppecalvaruso.library365.controllers;

import com.giuseppecalvaruso.library365.repositories.PrintedBookRepository;
import com.giuseppecalvaruso.library365.services.PrintedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/printedbooks")
public class PrintedBookController {
    @Autowired
    private PrintedBookService printedBookService;
}
