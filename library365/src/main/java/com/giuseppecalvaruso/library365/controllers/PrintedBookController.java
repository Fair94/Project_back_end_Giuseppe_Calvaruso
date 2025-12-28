package com.giuseppecalvaruso.library365.controllers;

import com.giuseppecalvaruso.library365.DTO.NewPrintedBookResponseDTO;
import com.giuseppecalvaruso.library365.DTO.PrintedBookDTO;
import com.giuseppecalvaruso.library365.entities.Author;
import com.giuseppecalvaruso.library365.entities.PrintedBook;
import com.giuseppecalvaruso.library365.services.AuthorService;
import com.giuseppecalvaruso.library365.services.PrintedBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class PrintedBookController {
    @Autowired
    private PrintedBookService printedBookService;
    @Autowired
    private AuthorService authorService;


    @GetMapping
    public List<PrintedBook> getPrintedBooks(){
        return this.printedBookService.getAllPrintedBooks();
    }

    @GetMapping("/{book_id}")
    public PrintedBook getBookById(@PathVariable("book_id") UUID book_id){
        return this.printedBookService.getPrintedBookById(book_id);
    }

    @GetMapping("author/{author_id}")
    public List<PrintedBook>getPrintedBooksByAuthorId(@PathVariable("author_id") UUID author_id){
        Author author = authorService.findAuthorByID(author_id);
        return printedBookService.findByAuthor(author_id);
    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','SUPERADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewPrintedBookResponseDTO createPrintedBook(@Valid @RequestBody PrintedBookDTO body){
        return this.printedBookService.save(body);
    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','SUPERADMIN')")
    @PutMapping({"/{book_id}"})
    public PrintedBook updatePrintedBookByID(@PathVariable("book_id") UUID book_id, @Valid @RequestBody PrintedBookDTO body){
        return this.printedBookService.findPrintedBookByIDAndUpdate(book_id,body);
    }


    @PreAuthorize("hasAnyAuthority('LIBRARIAN','SUPERADMIN')")
    @DeleteMapping("/{book_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePrintedBookByID(@PathVariable("book_id") UUID book_id){
        this.printedBookService.findByIdAndDelete(book_id);

    }


}
