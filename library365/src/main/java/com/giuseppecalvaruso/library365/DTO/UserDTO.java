package com.giuseppecalvaruso.library365.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserDTO(UUID user_id,
                      @NotBlank(message="Email is required")
                      @Email(message="Email must be valid")
                      @Size(max = 254, message = "Email too long ")
                      String email,

                      @NotBlank(message = "Password is required")
                      @Size(min=8, max =32, message = "Password length must be between 8 and 32 characters")
                      @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",
                      message = "Password must contain at least 1 uppercase letter, 1 lowercase letter and 1 number")
                      String password,

                      @NotBlank(message = "First Name is required")
                      @Size(max = 50, message = "First Name is too long ")
                      String firstName,

                      @NotBlank(message = "Last Name is required")
                      @Size(max = 50, message =  "Last Name is too long ")
                      String lastName,


                      LocalDateTime registration,

                      @Size(max = 2048, message = "Profile image URL too long ")
                      String url_pic
) {}
