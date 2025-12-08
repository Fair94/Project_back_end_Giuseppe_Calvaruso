package repositories;

import entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationRepositories extends JpaRepository<Reservation, UUID> {
}
