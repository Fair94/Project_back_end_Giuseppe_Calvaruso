package com.giuseppecalvaruso.library365.DTO;

import com.giuseppecalvaruso.library365.entities.ReservationStatus;

import java.time.LocalDateTime;

public record ReservationDTO(LocalDateTime created_at, ReservationStatus status) {

}
