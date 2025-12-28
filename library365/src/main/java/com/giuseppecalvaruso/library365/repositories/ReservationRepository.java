package com.giuseppecalvaruso.library365.repositories;

import com.giuseppecalvaruso.library365.ENUM.ReservationStatus;
import com.giuseppecalvaruso.library365.entities.Book;
import com.giuseppecalvaruso.library365.entities.Reservation;
import com.giuseppecalvaruso.library365.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findByUser(User user);

    boolean existsByUserAndBookAndStatus(User user, Book book, ReservationStatus status);
}
