package com.giuseppecalvaruso.library365.services;

import com.giuseppecalvaruso.library365.ENUM.ReservationStatus;
import com.giuseppecalvaruso.library365.entities.*;
import com.giuseppecalvaruso.library365.exceptions.NotFoundException;
import com.giuseppecalvaruso.library365.exceptions.ValidationException;
import com.giuseppecalvaruso.library365.repositories.BookRepository;
import com.giuseppecalvaruso.library365.repositories.ReservationRepository;
import com.giuseppecalvaruso.library365.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service

public class ReservationService {
    @Autowired private ReservationRepository reservationRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private BookRepository bookRepository;

    public List<Reservation> findAllReservation(){
        return reservationRepository.findAll();
    }

    public Reservation findReservationById(UUID reservation_id){
        return reservationRepository.findById(reservation_id)
                .orElseThrow(() -> new NotFoundException(reservation_id));
    }

    public List<Reservation> findByUser(UUID user_id){
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new NotFoundException(user_id));
        return reservationRepository.findByUser(user);
    }


    public Reservation createReservation(UUID user_id, UUID book_id){
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new NotFoundException(user_id));

        Book book = bookRepository.findById(book_id)
                .orElseThrow(() -> new NotFoundException(book_id));

        // Reservation = coda: la fai solo se il libro NON è disponibile
        if (book instanceof PrintedBook printed) {
            if (printed.getAvailableCopies() > 0) {
                throw new ValidationException("Book is available: create a loan instead of a reservation");
            }
        } else if (book instanceof EBook) {
            throw new ValidationException("EBook is available: reservation not allowed");
        }


        boolean alreadyPending = reservationRepository
                .existsReservation(user, book, ReservationStatus.PENDING);

        if (alreadyPending) {
            throw new ValidationException("Reservation already pending for this user/book");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setCreated_at(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.PENDING);

        return reservationRepository.save(reservation);
    }


    public Reservation cancelReservation(UUID reservation_id){
        Reservation reservation = findReservationById(reservation_id);

        if(reservation.getStatus() == ReservationStatus.FULFILLED){
            throw new ValidationException("Reservation already fulfilled");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        return reservationRepository.save(reservation);
    }

    public Reservation fulfillReservation(UUID reservation_id){
        Reservation reservation = findReservationById(reservation_id);

        if(reservation.getStatus() == ReservationStatus.CANCELLED){
            throw new ValidationException("Reservation already cancelled");
        }

        reservation.setStatus(ReservationStatus.FULFILLED);
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(UUID reservation_id){
        Reservation reservation = findReservationById(reservation_id);
        reservationRepository.delete(reservation);
    }


}
