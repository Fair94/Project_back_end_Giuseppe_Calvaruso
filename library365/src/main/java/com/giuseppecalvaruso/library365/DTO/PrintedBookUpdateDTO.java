package com.giuseppecalvaruso.library365.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record PrintedBookUpdateDTO(
        String title,

        @Size(min = 13, max = 13, message="ISBN must be of 13 characters")
        String ISBN,

        String description,

        @Min(value = 0, message="publication year must be positive")
        Integer publication_year,

        String position,

        @Min(value=0, message="total copies must be >= 0")
        Integer totalCopies,

        @Min(value=0, message="available copies must be >= 0")
        Integer availableCopies
) {}
