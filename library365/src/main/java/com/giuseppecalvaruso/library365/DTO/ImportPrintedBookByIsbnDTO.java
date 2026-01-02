package com.giuseppecalvaruso.library365.DTO;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record ImportPrintedBookByIsbnDTO(

        @NotBlank(message = "ISBN is required")
        @Size(min = 13, max = 13, message = "ISBN must be 13 characters")
        String ISBN,

        @NotEmpty(message = "At least one author is required")
        List<UUID> authorIds,

        @NotBlank(message = "Position is required")
        String position,

        @Min(value = 1, message = "total_copies must be >= 1")
        int total_copies

) {}

