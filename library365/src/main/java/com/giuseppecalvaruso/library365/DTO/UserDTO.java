package com.giuseppecalvaruso.library365.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserDTO(String email, String password,
                      String firstName, String lastName,
                      LocalDateTime registration, String url_pic) {
}
