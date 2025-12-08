package DTO;

import entities.LoanStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record LoanDTO(LocalDateTime loan_date, LocalDateTime due_date, LocalDateTime return_date, LoanStatus status) {
}
