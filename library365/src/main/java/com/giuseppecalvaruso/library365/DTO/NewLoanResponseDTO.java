package com.giuseppecalvaruso.library365.DTO;


import com.giuseppecalvaruso.library365.ENUM.LoanStatus;

import java.time.LocalDateTime;

import java.util.UUID;

public record NewLoanResponseDTO(UUID loan_id,
                                 LocalDateTime loan_date,
                                 java.time.LocalDate due_date,
                                 LocalDateTime return_date,
                                 LoanStatus loanStatus,
                                 UUID user_id,
                                 UUID book_id
                                 ){
}
