package com.giuseppecalvaruso.library365.repositories;

import com.giuseppecalvaruso.library365.ENUM.ReservationStatus;
import com.giuseppecalvaruso.library365.entities.Book;
import com.giuseppecalvaruso.library365.entities.Reservation;
import com.giuseppecalvaruso.library365.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findByUser(User user);

    @Query("""
        select (count(r) > 0)
        from Reservation r
        where r.user = :user and r.book = :book and r.status = :status
    """)
    boolean existsReservation(@Param("user") User user,
                              @Param("book") Book book,
                              @Param("status") ReservationStatus status);
}
