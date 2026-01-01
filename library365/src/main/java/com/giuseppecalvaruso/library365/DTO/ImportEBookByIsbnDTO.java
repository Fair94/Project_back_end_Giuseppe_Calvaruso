package com.giuseppecalvaruso.library365.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record ImportEBookByIsbnDTO(
        @NotBlank(message = "ISBN is required")
        @Size(min = 13, max = 13, message = "ISBN must be of 13 characters")
        String ISBN,

        @NotEmpty(message = "At least one author is required")
        List<UUID> authorIds,

        @NotBlank(message="File url is required")
        @Size(max = 2048, message = "File url is too long")
        String fileUrl,

        @NotBlank(message = "License is required ")
        String licenseType
) {}
