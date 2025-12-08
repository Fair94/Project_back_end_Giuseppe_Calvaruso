package repositories;

import entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface BookRepositories extends JpaRepository<Book, UUID> {
}
