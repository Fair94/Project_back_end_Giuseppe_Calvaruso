package com.giuseppecalvaruso.library365.controllers;

import com.giuseppecalvaruso.library365.DTO.AuthorDTO;
import com.giuseppecalvaruso.library365.entities.Author;
import com.giuseppecalvaruso.library365.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<Author> getAuthors(){
        return null;
    }

    @GetMapping("/author_id")
    public Author getAuthorById(@RequestParam UUID author_id){
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author createAuthor(@RequestBody AuthorDTO body){
        return null;
    }

    @PutMapping("/{author_id}")
    public Author updateAuthor(@PathVariable ("author_id")UUID author_id, @RequestBody AuthorDTO body){
        return null;
    }

    @DeleteMapping("/{author_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorById(@PathVariable("author_id") UUID author_id){}


}
