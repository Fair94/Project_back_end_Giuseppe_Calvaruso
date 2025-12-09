package com.giuseppecalvaruso.library365.DTO;

import com.giuseppecalvaruso.library365.entities.LoanStatus;

import java.time.LocalDateTime;

public record LoanDTO(LocalDateTime loan_date, LocalDateTime due_date, LocalDateTime return_date, LoanStatus status) {
}
