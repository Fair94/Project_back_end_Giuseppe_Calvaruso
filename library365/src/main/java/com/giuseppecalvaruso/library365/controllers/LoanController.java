package com.giuseppecalvaruso.library365.controllers;

import com.giuseppecalvaruso.library365.DTO.NewLoanResponseDTO;
import com.giuseppecalvaruso.library365.entities.Loan;
import com.giuseppecalvaruso.library365.repositories.LoanRepository;
import com.giuseppecalvaruso.library365.services.LoanService;
import com.giuseppecalvaruso.library365.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController

@RequestMapping("/users/{user_id}/loans")
public class LoanController {
    @Autowired
    private LoanService loanService;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<Loan> getLoans(@PathVariable("user_id") UUID user_id){
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewLoanResponseDTO createLoan(@PathVariable ("user_id") UUID user_id,
                                         @RequestBody NewLoanResponseDTO body){
        return null;
    }

    @PutMapping("/{loan_id}")
    public Loan updateLoanById(@PathVariable UUID user_id,
                               @PathVariable UUID loan_id,@RequestBody NewLoanResponseDTO body){
        return null;
    }

    @DeleteMapping("/{loan_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleLoanByID(@PathVariable("user_id") UUID user_id,@PathVariable UUID loan_id){

    }





}
