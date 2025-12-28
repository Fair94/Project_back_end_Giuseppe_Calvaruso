package com.giuseppecalvaruso.library365.DTO;

import com.giuseppecalvaruso.library365.ENUM.ReservationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record NewReservationResponseDTO(UUID reservation_id,
                                        LocalDateTime created_at,
                                        ReservationStatus status,
                                        UUID user_id,
                                        UUID book_id) {
}
