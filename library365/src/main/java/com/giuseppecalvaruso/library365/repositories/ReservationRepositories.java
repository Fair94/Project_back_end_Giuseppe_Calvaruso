package com.giuseppecalvaruso.library365.repositories;

import com.giuseppecalvaruso.library365.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationRepositories extends JpaRepository<Reservation, UUID> {
}
