package com.giuseppecalvaruso.library365.controllers;

import com.giuseppecalvaruso.library365.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ReservationController {
    @Autowired
    private ReservationService reservationService;
}
