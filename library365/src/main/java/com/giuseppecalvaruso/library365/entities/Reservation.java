package com.giuseppecalvaruso.library365.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.giuseppecalvaruso.library365.ENUM.ReservationStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="reservation")
public class Reservation {

    @Id
    @GeneratedValue
    @Column(name="reservation_id")
    private UUID reservation_id;

    @Column(name="created_at", nullable = false)
    private LocalDateTime created_at;

    @Column(name="status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id", nullable = false)
    @JsonIgnore
    private Book book;

    @JsonProperty("user_id")
    public UUID getUser_id() {
        return (user != null) ? user.getId() : null;
    }

    @JsonProperty("book_id")
    public UUID getBook_id() {
        return (book != null) ? book.getBook_id() : null;
    }

    public Reservation(LocalDateTime created_at, ReservationStatus status) {
        this.created_at = created_at;
        this.status = status;
    }

    public Reservation() {}

    public UUID getReservation_id() { return reservation_id; }

    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }

    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }


    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservation_id=" + reservation_id +
                ", created_at=" + created_at +
                ", status=" + status +
                '}';
    }
}
