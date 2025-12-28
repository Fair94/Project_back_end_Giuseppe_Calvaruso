package com.giuseppecalvaruso.library365.DTO;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record LoanDTO(
        @NotNull(message = "user_id is required")
        UUID user_id,

        @NotNull(message = "book_id is required")
        UUID book_id) {
}
