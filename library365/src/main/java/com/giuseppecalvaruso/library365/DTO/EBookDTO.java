package com.giuseppecalvaruso.library365.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record EBookDTO(UUID book_id,

                       @NotBlank(message="Title is required")
                       String title,

                       @NotBlank(message="ISBN is required")
                       @Size(min = 13, max = 13, message="ISBN must be of 13 characters")
                       String ISBN,

                       @Size(max=500,message = "make a shorter description")
                       String description,

                       @NotBlank(message="publication year is required")
                       int publication_year,

                       @Size(max = 2048, message="File url is too long")
                       String cover_url,

                       @Size(max = 2048,message = "File url is too long")
                       String fileUrl,

                       @NotBlank(message = "License is required ")
                       String licenseType) {
}
