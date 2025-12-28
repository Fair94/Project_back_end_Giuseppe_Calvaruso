package com.giuseppecalvaruso.library365.repositories;

import com.giuseppecalvaruso.library365.ENUM.LoanStatus;
import com.giuseppecalvaruso.library365.entities.Book;
import com.giuseppecalvaruso.library365.entities.Loan;
import com.giuseppecalvaruso.library365.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoanRepository extends JpaRepository<Loan, UUID> {

    List<Loan> findByUser(User user);

    List<Loan> findByBook(Book book);

    boolean existsByBookAndStatus(Book book, LoanStatus status);

    boolean existsByUserAndBookAndStatus(User user, Book book, LoanStatus status);
}
