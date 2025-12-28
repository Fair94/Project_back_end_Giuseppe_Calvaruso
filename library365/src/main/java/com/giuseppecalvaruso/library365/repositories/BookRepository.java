package com.giuseppecalvaruso.library365.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giuseppecalvaruso.library365.entities.Book;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
