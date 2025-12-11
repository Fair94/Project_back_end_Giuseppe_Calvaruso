package com.giuseppecalvaruso.library365.controllers;

import com.giuseppecalvaruso.library365.DTO.NewPrintedBookResponseDTO;
import com.giuseppecalvaruso.library365.entities.PrintedBook;
import com.giuseppecalvaruso.library365.services.PrintedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class PrintedBookController {
    @Autowired
    private PrintedBookService printedBookService;


    @GetMapping
    public List<PrintedBook> getPrintedBooks(){
        return null;
    }

    @GetMapping("/{book_id}")
    public PrintedBook getBookById(@PathVariable("book_id") UUID book_id){
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewPrintedBookResponseDTO createPrintedBook(@RequestBody NewPrintedBookResponseDTO body){
        return null;
    }

    @PutMapping({"/{book_id}"})
    public PrintedBook updatePrintedBookByID(@PathVariable("book_id") UUID book_id){
        return null;
    }

    @DeleteMapping("/{book_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePrintedBookByID(@PathVariable("book_id") UUID book_id){

    }


}
