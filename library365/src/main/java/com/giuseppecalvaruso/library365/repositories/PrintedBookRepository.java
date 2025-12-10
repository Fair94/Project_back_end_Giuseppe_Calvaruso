package com.giuseppecalvaruso.library365.repositories;

import com.giuseppecalvaruso.library365.entities.PrintedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PrintedBookRepository extends JpaRepository<PrintedBook, UUID> {
}
