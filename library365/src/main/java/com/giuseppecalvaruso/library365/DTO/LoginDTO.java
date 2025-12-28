package com.giuseppecalvaruso.library365.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Size(max = 254, message = "Email too long")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 32, message = "Password length must be between 8 and 32 characters")
        String password
) {}
