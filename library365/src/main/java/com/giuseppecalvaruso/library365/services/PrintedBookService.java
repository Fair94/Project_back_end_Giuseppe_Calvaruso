package com.giuseppecalvaruso.library365.services;

import com.giuseppecalvaruso.library365.DTO.*;
import com.giuseppecalvaruso.library365.entities.PrintedBook;
import com.giuseppecalvaruso.library365.exceptions.EBookNotFoundException;
import com.giuseppecalvaruso.library365.exceptions.NotFoundException;
import com.giuseppecalvaruso.library365.exceptions.ValidationException;
import com.giuseppecalvaruso.library365.repositories.PrintedBookRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PrintedBookService {
    @Autowired
    private PrintedBookRepository printedBookRepository;

    public List<PrintedBook> getAllPrintedBooks(){
        return printedBookRepository.findAll();
    }


    public PrintedBook getPrintedBookById(UUID book_id){
        return printedBookRepository.findById(book_id).orElseThrow(()->new NotFoundException(book_id));
    }

    public NewPrintedBookResponseDTO save(PrintedBookDTO body){

        if (body.title()== null || body.ISBN()==null )
            throw new ValidationException("Title or ISBN or publication year is required");

        String title = body.title().trim();
        String ISBN = body.ISBN().trim();

        if (printedBookRepository.findByISBNIgnoreCase(ISBN).isPresent()) {
            throw new ValidationException("ISBN already exists");
        }


        int publication_year = body.publication_year();

        if (title.length() < 3 || ISBN.length() != 13)
            throw new ValidationException("Title must be at least 3 characters and ISBN must be 13 characters");



        PrintedBook newPrintedBook  = new PrintedBook(
                title,
                ISBN,
                body.description(),
                body.publication_year(),
                body.position(),
                body.total_copies(),
                body.available_copies()

        );

        PrintedBook savedPrintedbook =printedBookRepository.save(newPrintedBook);

        return new NewPrintedBookResponseDTO(savedPrintedbook.getBook_id());
    }

    public PrintedBook findPrintedBookByIDAndUpdate(UUID book_id, PrintedBookUpdateDTO body){
        PrintedBook found = printedBookRepository.findById(book_id)
                .orElseThrow(() -> new NotFoundException(book_id));

        if (body.title() != null) found.setTitle(body.title().trim());
        if (body.ISBN() != null) found.setISBN(body.ISBN().trim());
        if (body.description() != null) found.setDescription(body.description());
        if (body.publication_year() != null) found.setPublication_year(body.publication_year());
        if (body.position() != null) found.setPosition(body.position().trim());
        if (body.totalCopies() != null) found.setTotalCopies(body.totalCopies());
        if (body.availableCopies() != null) found.setAvailableCopies(body.availableCopies());

        if (found.getAvailableCopies() > found.getTotalCopies()) {
            throw new ValidationException("availableCopies cannot be greater than totalCopies");
        }

        return printedBookRepository.save(found);
    }


    public void findByIdAndDelete(UUID book_id){
        PrintedBook foundPrintedbook =  printedBookRepository.findById(book_id).orElseThrow(()->new NotFoundException(book_id));
        this.printedBookRepository.delete(foundPrintedbook);
    }

    public PrintedBook updatePrintedBookPosition(UUID book_id, String position){
        if (position==null || position.trim().isBlank() ){
            throw new ValidationException("position is required");
        }
        PrintedBook printedBook = this.getPrintedBookById(book_id);
        String normalized_position = position.trim();
        printedBook.setPosition(normalized_position);
        return this.printedBookRepository.save(printedBook);
    }


    public List <PrintedBook> getPrintedBookByAuthor(String nameSurnameAuthor){
        if(nameSurnameAuthor==null || nameSurnameAuthor.trim().isBlank()){
            throw new ValidationException("Author name or Surname are required");

        }


        return printedBookRepository.findByAuthorNameOrSurnameLike(nameSurnameAuthor.trim());

    }

    public List<PrintedBook> getPrintedBookByAuthorFullName(String firstName, String lastName){
        if(firstName==null || lastName==null || lastName.trim().isBlank() || firstName.trim().isBlank()){
            throw new ValidationException("Author name or Surname are required");
        }
        return printedBookRepository.findByAuthorFullName(firstName.trim(), lastName.trim());
    }

    public PrintedBook getPrintedBookByISBN(String ISBN){
        if (ISBN == null || ISBN.trim().isBlank()){
            throw new ValidationException("ISBN  is required");
        }

        String normalized_ISBN = ISBN.trim();

        return printedBookRepository.findByISBNIgnoreCase(normalized_ISBN).orElseThrow(()-> new EBookNotFoundException(ISBN));
    }

    public List<PrintedBook> findByAuthor(UUID author_id){

        return printedBookRepository.findByAuthors_Id(author_id);
    }



}
