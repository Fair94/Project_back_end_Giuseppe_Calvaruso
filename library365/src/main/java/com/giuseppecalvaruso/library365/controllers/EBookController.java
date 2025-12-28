package com.giuseppecalvaruso.library365.controllers;

import com.giuseppecalvaruso.library365.DTO.EBookDTO;
import com.giuseppecalvaruso.library365.DTO.NewEBookResponseDTO;
import com.giuseppecalvaruso.library365.entities.Author;
import com.giuseppecalvaruso.library365.entities.EBook;
import com.giuseppecalvaruso.library365.services.AuthorService;
import com.giuseppecalvaruso.library365.services.EBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ebooks")
public class EBookController {
    @Autowired
    private EBookService EBookService;
    @Autowired
    private AuthorService authorService;


    @GetMapping
    public List<EBook> getAllEBooks(){
        return this.EBookService.getAllEBooks();
    }


    @GetMapping("/{book_id}")
    public EBook getEBookById(@PathVariable("book_id") UUID book_id){
        return this.EBookService.getEBookById(book_id);
    }


    @GetMapping("author/{author_id}")
    public List<EBook> getEBookByAuthor(@PathVariable UUID author_id){
        Author author = authorService.findAuthorByID(author_id);
        return EBookService.findByAuthor(author_id);
    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','SUPERADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewEBookResponseDTO createEBook( @Valid @RequestBody EBookDTO body){
        return this.EBookService.save(body);
    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','SUPERADMIN')")
    @PutMapping("/{book_id}")
    public EBook updateEBook(@PathVariable("book_id") UUID book_id, @Valid @RequestBody EBookDTO body){
        return this.EBookService.findEBookByIDAndUpdate(book_id,body);
    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','SUPERADMIN')")
    @DeleteMapping("/{book_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEBook(@PathVariable("book_id") UUID book_id){
        this.EBookService.findByIdAndDelete(book_id);

    }




}
