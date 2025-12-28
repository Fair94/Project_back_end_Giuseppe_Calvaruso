package com.giuseppecalvaruso.library365.services;

import com.giuseppecalvaruso.library365.DTO.EBookDTO;
import com.giuseppecalvaruso.library365.DTO.NewEBookResponseDTO;
import com.giuseppecalvaruso.library365.DTO.NewPrintedBookResponseDTO;
import com.giuseppecalvaruso.library365.DTO.PrintedBookDTO;
import com.giuseppecalvaruso.library365.entities.Author;
import com.giuseppecalvaruso.library365.entities.EBook;
import com.giuseppecalvaruso.library365.entities.PrintedBook;
import com.giuseppecalvaruso.library365.exceptions.EBookNotFoundException;
import com.giuseppecalvaruso.library365.exceptions.NotFoundException;
import com.giuseppecalvaruso.library365.exceptions.ValidationException;
import com.giuseppecalvaruso.library365.repositories.PrintedBookRepository;
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

        if (body.Title()== null || body.ISBN()==null )
            throw new ValidationException("Title or ISBN or publication year is required");

        String title = body.Title().trim();
        String ISBN = body.ISBN().trim();
        int publication_year = body.publication_year();

        if (title.length()<3 || ISBN.length()<3 || ISBN.length()>13)
            throw new ValidationException("Title or ISBN are of an invalid length");


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

    public PrintedBook findPrintedBookByIDAndUpdate(UUID book_id, PrintedBookDTO body){
        PrintedBook foundPrintedbook =  printedBookRepository.findById(book_id).orElseThrow(()->new NotFoundException(book_id));

        if (body.Title()!= null ) foundPrintedbook.setTitle(body.Title());
        if (body.ISBN()!= null) foundPrintedbook.setISBN(body.ISBN());
        if (body.description()!= null) foundPrintedbook.setDescription(body.description());




        return this.printedBookRepository.save(foundPrintedbook);

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
