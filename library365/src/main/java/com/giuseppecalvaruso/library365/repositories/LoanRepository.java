package com.giuseppecalvaruso.library365.repositories;

import com.giuseppecalvaruso.library365.ENUM.LoanStatus;
import com.giuseppecalvaruso.library365.entities.Book;
import com.giuseppecalvaruso.library365.entities.Loan;
import com.giuseppecalvaruso.library365.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoanRepository extends JpaRepository<Loan, UUID> {

    List<Loan> findByUser(User user);



    @Query("select (count(l) > 0) from Loan l where l.book = :book and l.status = :status")
    boolean existsActiveLoanByBook(@Param("book") Book book, @Param("status") LoanStatus status);


}
