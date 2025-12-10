package com.giuseppecalvaruso.library365.services;

import com.giuseppecalvaruso.library365.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;
}
