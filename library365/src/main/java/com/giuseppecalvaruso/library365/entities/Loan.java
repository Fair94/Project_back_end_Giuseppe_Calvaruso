package com.giuseppecalvaruso.library365.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.giuseppecalvaruso.library365.ENUM.LoanStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="loan")
public class Loan {

    @Id
    @GeneratedValue
    @Column(name = "loan_id")
    private UUID loan_id;

    @Column(name ="loan_date", nullable = false)
    private LocalDateTime loan_date;

    @Column(name = "due_date", nullable = false)
    private LocalDate due_date;

    @Column(name="return_date")
    private LocalDateTime return_date;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id", nullable = false)
    @JsonIgnore
    private Book book;

    @JsonProperty("user_id")
    public UUID getUser_id() {
        return (user != null) ? user.getId() : null;
    }

    @JsonProperty("book_id")
    public UUID getBook_id() {
        return (book != null) ? book.getBook_id() : null;
    }

    public Loan(LocalDateTime loan_date, LocalDate due_date, LocalDateTime return_date, LoanStatus status) {
        this.loan_date = loan_date;
        this.due_date = due_date;
        this.return_date = return_date;
        this.status = status;
    }

    public Loan() {}

    public UUID getLoan_id() { return loan_id; }

    public LocalDateTime getLoan_date() { return loan_date; }
    public void setLoan_date(LocalDateTime loan_date) { this.loan_date = loan_date; }

    public LocalDate getDue_date() { return due_date; }
    public void setDue_date(LocalDate due_date) { this.due_date = due_date; }

    public LocalDateTime getReturn_date() { return return_date; }
    public void setReturn_date(LocalDateTime return_date) { this.return_date = return_date; }

    public LoanStatus getStatus() { return status; }
    public void setStatus(LoanStatus status) { this.status = status; }


    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    @Override
    public String toString() {
        return "Loan{" +
                "loan_id=" + loan_id +
                ", loan_date=" + loan_date +
                ", due_date=" + due_date +
                ", return_date=" + return_date +
                ", status=" + status +
                '}';
    }
}
