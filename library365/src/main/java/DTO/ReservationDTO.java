package DTO;

import entities.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservationDTO(LocalDateTime created_at, ReservationStatus status) {

}
