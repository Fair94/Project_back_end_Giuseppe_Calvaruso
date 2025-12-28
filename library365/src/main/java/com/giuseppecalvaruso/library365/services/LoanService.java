package com.giuseppecalvaruso.library365.services;

import com.giuseppecalvaruso.library365.DTO.LoanDTO;
import com.giuseppecalvaruso.library365.ENUM.LoanStatus;
import com.giuseppecalvaruso.library365.entities.Book;
import com.giuseppecalvaruso.library365.entities.Loan;
import com.giuseppecalvaruso.library365.entities.PrintedBook;
import com.giuseppecalvaruso.library365.entities.User;

import com.giuseppecalvaruso.library365.exceptions.NotFoundException;
import com.giuseppecalvaruso.library365.exceptions.ValidationException;
import com.giuseppecalvaruso.library365.repositories.BookRepository;
import com.giuseppecalvaruso.library365.repositories.LoanRepository;
import com.giuseppecalvaruso.library365.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    public List<Loan> getAllLoans(){
        return loanRepository.findAll();
    }

    public Loan getLoanById(UUID loan_id){
        return loanRepository.findById(loan_id).orElseThrow(()-> new NotFoundException(loan_id));
    }

    public List<Loan> getLoanByUserId(User user){
       return loanRepository.findByUser(user);
    }

    public Loan createLoan(UUID user_id, UUID book_id){
        User user = userRepository.findById(user_id).orElseThrow(()-> new NotFoundException(user_id));
        Book book = bookRepository.findById(book_id).orElseThrow(()-> new NotFoundException(book_id));

        if (loanRepository.existsByBookAndStatus( book, LoanStatus.ACTIVE)){
            throw new ValidationException("Book already loaned??");
        }

        if (book instanceof PrintedBook printedBook) {
            if (printedBook.getAvailableCopies() <= 0) {
                throw new ValidationException("Book has no available copies??");}
                printedBook.setAvailableCopies(printedBook.getAvailableCopies() - 1);
                bookRepository.save(printedBook);
            }
            Loan newLoan = new Loan();
            newLoan.setUser(user);
            newLoan.setBook(book);

            LocalDateTime now = LocalDateTime.now();
            newLoan.setLoan_date(now);
            newLoan.setDue_date(LocalDate.from(now.plusDays(14)));
            newLoan.setReturn_date(null);
            newLoan.setStatus(LoanStatus.ACTIVE);

            return loanRepository.save(newLoan);

        }
        public Loan returnedLoan(UUID loan_id){
        Loan loan = this.getLoanById(loan_id);

            if(loan.getStatus() == LoanStatus.RETURNED) return loan;

            loan.setReturn_date(LocalDateTime.now());
            loan.setStatus(LoanStatus.RETURNED);

            Book book = loan.getBook();
            if(book instanceof PrintedBook printedBook) {
                printedBook.setAvailableCopies(printedBook.getAvailableCopies() +1 );
                bookRepository.save(printedBook);
        }
            return loanRepository.save(loan);
        }

        public void deleteLoan(UUID loan_id){
            Loan loan = this.getLoanById(loan_id);
            loanRepository.delete(loan);
        }

    }
