package com.giuseppecalvaruso.library365.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthorDTO(
        @NotBlank(message = "Author First Name is required")
        @Size(min = 3, max = 50, message = "Author First Name must be 3-50 chars")
        String firstName,

        @NotBlank(message = "Author Last Name is required")
        @Size(min = 3, max = 50, message = "Author Last Name must be 3-50 chars")
        String lastName,

        @Size(max = 500, message = "Biography is too long")
        String bio
) {}
