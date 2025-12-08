package entities;


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

    @Column(name="created_at",nullable = false)
    private LocalDateTime created_at;

    @Column(name="status", nullable = false)
    @Enumerated (EnumType.STRING)
    private ReservationStatus status;

    public Reservation(LocalDateTime created_at, ReservationStatus status) {
        this.created_at = created_at;
        this.status = status;
    }

    public Reservation() {

    }

    /* Here goes relationship*/

    public UUID getReservation_id() {
        return reservation_id;
    }



    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservation_id=" + reservation_id +
                ", created_at=" + created_at +
                ", status=" + status +
                '}';
    }
}
