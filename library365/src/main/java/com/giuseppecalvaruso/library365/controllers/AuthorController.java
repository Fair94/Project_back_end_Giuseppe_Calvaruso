package com.giuseppecalvaruso.library365.controllers;

import com.giuseppecalvaruso.library365.entities.Author;
import com.giuseppecalvaruso.library365.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
