package com.giuseppecalvaruso.library365.services;

import com.giuseppecalvaruso.library365.DTO.*;
import com.giuseppecalvaruso.library365.entities.Author;
import com.giuseppecalvaruso.library365.entities.EBook;
import com.giuseppecalvaruso.library365.entities.PrintedBook;
import com.giuseppecalvaruso.library365.exceptions.ValidationException;
import com.giuseppecalvaruso.library365.repositories.EbookRepository;
import com.giuseppecalvaruso.library365.repositories.PrintedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookImportService {

    @Autowired private EbookRepository ebookRepository;
    @Autowired private PrintedBookRepository printedBookRepository;
    @Autowired private AuthorService authorService;
    @Autowired private GoogleBooksService googleBooksService;

    public NewEBookResponseDTO importEBookByIsbn(ImportEBookByIsbnDTO body) {

        if (body == null || body.ISBN() == null) throw new ValidationException("ISBN is required");
        String ISBN = body.ISBN().trim();
        if (ISBN.length() != 13) throw new ValidationException("ISBN must be 13 characters");

        if (body.fileUrl() == null || body.fileUrl().trim().isBlank()) throw new ValidationException("File url is required");
        if (body.licenseType() == null || body.licenseType().trim().isBlank()) throw new ValidationException("License type is required");
        if (body.authorIds() == null || body.authorIds().isEmpty()) throw new ValidationException("At least one author is required");

        if (ebookRepository.findByISBNIgnoreCase(ISBN).isPresent()
                || printedBookRepository.findByISBNIgnoreCase(ISBN).isPresent()) {
            throw new ValidationException("ISBN already exists");
        }

        GoogleBookDTO gb = googleBooksService.fetchByIsbn(ISBN);

        EBook newEBook = new EBook(
                gb.title(),
                gb.isbn(),
                gb.description(),
                gb.publicationYear(),
                body.fileUrl().trim(),
                body.licenseType().trim()
        );

        List<Author> authors = body.authorIds().stream()
                .map(authorService::findAuthorByID)
                .toList();
        newEBook.setAuthors(authors);

        EBook saved = ebookRepository.save(newEBook);
        return new NewEBookResponseDTO(saved.getBook_id());
    }

    public NewPrintedBookResponseDTO importPrintedBookByIsbn(ImportPrintedBookByIsbnDTO body) {

        if (body == null || body.ISBN() == null) throw new ValidationException("ISBN is required");
        String ISBN = body.ISBN().trim();
        if (ISBN.length() != 13) throw new ValidationException("ISBN must be 13 characters");

        if (body.position() == null || body.position().trim().isBlank()) throw new ValidationException("Position is required");
        if (body.total_copies() < 1) throw new ValidationException("total_copies must be >= 1");
        if (body.authorIds() == null || body.authorIds().isEmpty()) throw new ValidationException("At least one author is required");

        if (printedBookRepository.findByISBNIgnoreCase(ISBN).isPresent()
                || ebookRepository.findByISBNIgnoreCase(ISBN).isPresent()) {
            throw new ValidationException("ISBN already exists");
        }

        GoogleBookDTO gb = googleBooksService.fetchByIsbn(ISBN);

        PrintedBook newPrintedBook = new PrintedBook(
                gb.title(),
                gb.isbn(),
                gb.description(),
                gb.publicationYear(),
                body.position().trim(),
                body.total_copies(),
                body.total_copies()
        );

        List<Author> authors = body.authorIds().stream()
                .map(authorService::findAuthorByID)
                .toList();
        newPrintedBook.setAuthors(authors);

        PrintedBook saved = printedBookRepository.save(newPrintedBook);
        return new NewPrintedBookResponseDTO(saved.getBook_id());
    }
}
