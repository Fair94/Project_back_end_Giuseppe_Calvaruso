package com.giuseppecalvaruso.library365.services;

import com.giuseppecalvaruso.library365.entities.Book;
import com.giuseppecalvaruso.library365.exceptions.NotFoundException;
import com.giuseppecalvaruso.library365.repositories.BookRepository;
import com.giuseppecalvaruso.library365.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book findById(UUID book_id) {
        return bookRepository.findById(book_id).orElseThrow(()-> new NotFoundException(book_id));
    }
}
