package com.giuseppecalvaruso.library365.controllers;

import com.giuseppecalvaruso.library365.DTO.NewEBookResponseDTO;
import com.giuseppecalvaruso.library365.entities.EBook;
import com.giuseppecalvaruso.library365.services.EBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ebooks")
public class EBookController {
    @Autowired
    private EBookService EBookService;

    @GetMapping
    public List<EBook> getAllEBooks(){
        return this.EBookService.getAllEBooks();
    }

    @GetMapping("/{book_id}")
    public EBook getEBookById(@PathVariable("book_id") UUID book_id){
        return null;
    }

    @GetMapping("/{author}")
    public EBook getEBookByAuthor(@PathVariable("author") String author){
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EBook createEBook(@RequestBody NewEBookResponseDTO body){
        return null;
    }

    @PutMapping("/{book_id}")
    public EBook updateEBook(@PathVariable("book_id") UUID book_id, @RequestBody NewEBookResponseDTO body){
        return null;
    }

    @DeleteMapping("/{book_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEBook(@PathVariable("book_id") UUID book_id){

    }




}
