package com.giuseppecalvaruso.library365.repositories;

import com.giuseppecalvaruso.library365.entities.EBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EbookRepository extends JpaRepository<EBook, UUID> {
    boolean existsByAuthors_AuthorId(UUID author_id);

}
