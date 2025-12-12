package com.giuseppecalvaruso.library365.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserDTO(UUID user_id, String email, String password,
                      String firstName, String lastName,
                      LocalDateTime registration, String url_pic) {

}
