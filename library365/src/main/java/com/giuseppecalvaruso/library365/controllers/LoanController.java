package com.giuseppecalvaruso.library365.controllers;

import com.giuseppecalvaruso.library365.repositories.LoanRepository;
import com.giuseppecalvaruso.library365.services.LoanService;
import com.giuseppecalvaruso.library365.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class LoanController {
    @Autowired
    private LoanService loanService;



}
