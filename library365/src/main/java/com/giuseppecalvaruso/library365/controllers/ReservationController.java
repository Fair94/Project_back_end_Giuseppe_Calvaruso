package com.giuseppecalvaruso.library365.controllers;

import com.giuseppecalvaruso.library365.DTO.NewReservationResponseDTO;
import com.giuseppecalvaruso.library365.entities.Reservation;
import com.giuseppecalvaruso.library365.services.ReservationService;
import com.giuseppecalvaruso.library365.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/{user_id}/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<Reservation> getAllReservations(@PathVariable("user_id") UUID user_id){
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation createReservation(@PathVariable("user_id") @RequestBody Reservation reservation){
        return null;
    }

    @PutMapping("/{reservation_id}")
    public Reservation updateReservationByID(@PathVariable UUID user_id,
                                             @PathVariable UUID reservation_id,
                                             @RequestBody NewReservationResponseDTO reservation){
        return null;
    }

    @DeleteMapping("/{reservation_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservationByID(@PathVariable("user_id") UUID user_id,
                                      @PathVariable UUID reservation_id){

    }

}
