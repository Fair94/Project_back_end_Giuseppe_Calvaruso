package repositories;

import entities.EBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EbookInterfaces extends JpaRepository<EBook, UUID> {
}
