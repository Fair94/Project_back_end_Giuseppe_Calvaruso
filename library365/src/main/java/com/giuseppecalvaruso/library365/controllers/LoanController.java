package com.giuseppecalvaruso.library365.controllers;

import com.giuseppecalvaruso.library365.DTO.LoanDTO;
import com.giuseppecalvaruso.library365.DTO.NewLoanResponseDTO;
import com.giuseppecalvaruso.library365.entities.Loan;
import com.giuseppecalvaruso.library365.entities.User;
import com.giuseppecalvaruso.library365.exceptions.ValidationException;
import com.giuseppecalvaruso.library365.services.LoanService;
import com.giuseppecalvaruso.library365.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','SUPERADMIN')")
    @GetMapping
    public List<Loan> getLoans(@PathVariable("user_id") UUID user_id){
        return loanService.getLoansByUserId(user_id);
    }



    @PreAuthorize("hasAnyAuthority('LIBRARIAN','SUPERADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewLoanResponseDTO createLoan(@PathVariable ("user_id") UUID user_id,
                                         @RequestBody @Valid LoanDTO body){
        Loan created = loanService.createLoan(user_id,body.book_id());

        return new NewLoanResponseDTO(
                created.getLoan_id(),
                created.getLoan_date(),
                created.getDue_date(),
                created.getReturn_date(),
                created.getStatus(),
                created.getUser().getId(),
                created.getBook().getBook_id()
        );

    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','SUPERADMIN')")
    @PutMapping("/{loan_id}")
    public NewLoanResponseDTO updateLoanById(@PathVariable("user_id") UUID user_id,
                                             @PathVariable("loan_id") UUID loan_id)
    {

        Loan updateLoan = loanService.getLoanById(loan_id);

        if(!updateLoan.getUser().getId().equals(user_id)){
            throw new ValidationException("User does not belong to this loan");
        }

        Loan updated = loanService.returnedLoan(loan_id);

        return new NewLoanResponseDTO(
                updated.getLoan_id(),
                updated.getLoan_date(),
                updated.getDue_date(),
                updated.getReturn_date(),
                updated.getStatus(),
                updated.getUser().getId(),
                updated.getBook().getBook_id()
        );

    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','SUPERADMIN')")
    @DeleteMapping("/{loan_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLoanByID(@PathVariable("user_id") UUID user_id,
                               @PathVariable("loan_id") UUID loan_id)
    {
        Loan deletedLoan=loanService.getLoanById(loan_id);

        if(!deletedLoan.getUser().getId().equals(user_id)){
            throw new ValidationException("User does not belong to this loan");
        }
        loanService.deleteLoan(loan_id);


    }





}
