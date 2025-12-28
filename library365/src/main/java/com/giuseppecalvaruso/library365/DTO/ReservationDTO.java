package com.giuseppecalvaruso.library365.DTO;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReservationDTO(
        @NotNull(message= "user_id required")
        UUID user_id,
        @NotNull(message= "book_id required")
        UUID book_id

) {

}
