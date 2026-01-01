package com.giuseppecalvaruso.library365.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record PrintedBookDTO(
        @NotBlank(message="Title is required")
        String title,

        @NotBlank(message="ISBN is required")
        @Size(min = 13, max = 13, message="ISBN must be of 13 characters")
        String ISBN,

        @Size(max=500,message = "make a shorter description")
        String description,

        @NotNull(message="publication year is required")
        @Min(value = 0, message="publication year must be positive")
        Integer publication_year,

        @NotEmpty(message="authorIds is required")
        List<UUID> authorIds,

        @NotBlank(message="Position is required")
        String position,

        @Min(value=0, message="total copies must be >= 0")
        int total_copies,

        @Min(value=0, message="available copies must be >= 0")
        int available_copies
) {}
