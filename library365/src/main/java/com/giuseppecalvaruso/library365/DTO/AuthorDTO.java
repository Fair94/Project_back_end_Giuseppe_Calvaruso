package com.giuseppecalvaruso.library365.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record AuthorDTO(UUID author_id,
                        @NotBlank(message = "Author First Name is required ")
                        @Size(max = 50, message = "Author First Name is too long ")
                        String firstName,

                        @NotBlank(message = "Author Last Name is required")
                        @Size(max = 50, message =  " Author Last Name is too long ")
                        String lastName,


                        @Size(max = 500, message = "Biography is too long  ")
                        String bio) {
}
