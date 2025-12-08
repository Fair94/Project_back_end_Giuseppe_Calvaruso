package entities;

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

    @Column(name ="loan_date",nullable = false)
    private LocalDateTime loan_date;

    @Column(name = "due_date",nullable = false)
    private LocalDate due_date;

    @Column(name="return_date",nullable = false)
    private LocalDateTime return_date;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    public Loan(LocalDateTime loan_date, LocalDate due_date, LocalDateTime return_date, LoanStatus status) {
        this.loan_date = loan_date;
        this.due_date = due_date;
        this.return_date = return_date;
        this.status = status;
    }

    public Loan() {

    }
    //Here I have to put relation with other entities

    public UUID getLoan_id() {
        return loan_id;
    }



    public LocalDateTime getLoan_date() {
        return loan_date;
    }

    public void setLoan_date(LocalDateTime loan_date) {
        this.loan_date = loan_date;
    }

    public LocalDate getDue_date() {
        return due_date;
    }

    public void setDue_date(LocalDate due_date) {
        this.due_date = due_date;
    }

    public LocalDateTime getReturn_date() {
        return return_date;
    }

    public void setReturn_date(LocalDateTime return_date) {
        this.return_date = return_date;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

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
