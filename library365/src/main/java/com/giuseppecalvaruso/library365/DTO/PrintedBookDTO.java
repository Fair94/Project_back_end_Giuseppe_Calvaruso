package com.giuseppecalvaruso.library365.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PrintedBookDTO(
        @NotBlank(message="Title is required")
        String Title,

        @NotBlank(message="ISBN is required")
        @Size(min = 13, max = 13, message="ISBN must be of 13 characters")
        String ISBN,

        @Size(max=500,message = "make a shorter description")
        String description,


        @NotBlank(message="publication year is required")
        int publication_year,


        @NotBlank(message="Position is required")
        String position,

        @NotNull @Min(0)
        int total_copies,

        @NotNull @Min(0)
        int available_copies) {
}
