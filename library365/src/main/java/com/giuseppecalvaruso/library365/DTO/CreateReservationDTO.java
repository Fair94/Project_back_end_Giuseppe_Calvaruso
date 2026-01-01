package com.giuseppecalvaruso.library365.DTO;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateReservationDTO(
        @NotNull(message = "book_id is required")
        UUID book_id
) {}
