package com.giuseppecalvaruso.library365.controllers;

import com.giuseppecalvaruso.library365.DTO.AuthorDTO;
import com.giuseppecalvaruso.library365.DTO.NewAuthorResponseDTO;
import com.giuseppecalvaruso.library365.entities.Author;
import com.giuseppecalvaruso.library365.services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping
    public List<Author> getAuthors(){

        return this.authorService.getAuthors();
    }


    @GetMapping("/{author_id}")
    public Author getAuthorById(@PathVariable UUID author_id){

        return this.authorService.getAuthorByID(author_id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewAuthorResponseDTO createAuthor(@Valid @RequestBody AuthorDTO body){

        return this.authorService.save(body);
    }

    @PutMapping("/{author_id}")
    public Author updateAuthorByID(@PathVariable ("author_id")UUID author_id,
                                   @Valid  @RequestBody AuthorDTO body){
        return this.authorService.findAuthorByID(author_id, body);
    }

    @DeleteMapping("/{author_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorById(@PathVariable("author_id") UUID author_id){
        this.authorService.findByIDAndDelete(author_id);
    }


}
