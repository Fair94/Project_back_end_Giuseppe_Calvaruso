package com.giuseppecalvaruso.library365.controllers;

import com.giuseppecalvaruso.library365.DTO.NewReservationResponseDTO;
import com.giuseppecalvaruso.library365.entities.Reservation;
import com.giuseppecalvaruso.library365.exceptions.ValidationException;
import com.giuseppecalvaruso.library365.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.giuseppecalvaruso.library365.DTO.CreateReservationDTO;
import jakarta.validation.Valid;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/{user_id}/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','SUPERADMIN')")
    @GetMapping
    public List<Reservation> getAllReservations(@PathVariable("user_id") UUID user_id) {

        return reservationService.findByUser(user_id);
    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','SUPERADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewReservationResponseDTO createReservation(
            @PathVariable("user_id") UUID user_id,
            @RequestBody @Valid CreateReservationDTO body
    ) {

        Reservation createdReservation = reservationService.createReservation(user_id, body.book_id());

        return new NewReservationResponseDTO(
                createdReservation.getReservation_id(),
                createdReservation.getCreated_at(),
                createdReservation.getStatus(),
                createdReservation.getUser().getId(),
                createdReservation.getBook().getBook_id()
        );
    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','SUPERADMIN')")
    @PutMapping("/{reservation_id}")
    public NewReservationResponseDTO updateReservationByID(
            @PathVariable("user_id") UUID user_id,
            @PathVariable("reservation_id") UUID reservation_id,
            @RequestParam("action") String action
    ) {
        Reservation currentReservation = reservationService.findReservationById(reservation_id);

        if (!currentReservation.getUser().getId().equals(user_id)) {
            throw new ValidationException("Reservation does not belong to this User");
        }

        Reservation updated;

        if ("cancel".equalsIgnoreCase(action)) {
            updated = reservationService.cancelReservation(reservation_id);
        } else if ("fulfill".equalsIgnoreCase(action)) {
            updated = reservationService.fulfillReservation(reservation_id);
        } else {
            throw new ValidationException("Invalid action. Use cancel or fulfill.");
        }

        return new NewReservationResponseDTO(
                updated.getReservation_id(),
                updated.getCreated_at(),
                updated.getStatus(),
                updated.getUser().getId(),
                updated.getBook().getBook_id()
        );
    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','SUPERADMIN')")
    @DeleteMapping("/{reservation_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservationByID(
            @PathVariable("user_id") UUID user_id,
            @PathVariable("reservation_id") UUID reservation_id
    ) {
        Reservation reservation = reservationService.findReservationById(reservation_id);

        if (!reservation.getUser().getId().equals(user_id)) {
            throw new ValidationException("Reservation does not belong to this user");
        }

        reservationService.deleteReservation(reservation_id);
    }
}
