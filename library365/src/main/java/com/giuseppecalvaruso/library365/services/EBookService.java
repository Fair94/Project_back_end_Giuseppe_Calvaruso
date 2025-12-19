package com.giuseppecalvaruso.library365.services;

import com.giuseppecalvaruso.library365.DTO.EBookDTO;
import com.giuseppecalvaruso.library365.DTO.NewEBookResponseDTO;
import com.giuseppecalvaruso.library365.entities.EBook;
import com.giuseppecalvaruso.library365.exceptions.NotFoundException;
import com.giuseppecalvaruso.library365.exceptions.ValidationException;
import com.giuseppecalvaruso.library365.repositories.EbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EBookService {
    @Autowired
    private EbookRepository ebookRepository ;

    public List<EBook> getAllEBooks(){
        return ebookRepository.findAll();
    }

    public EBook getEBookById(UUID book_id){
        return ebookRepository.findById(book_id).orElseThrow(()->new NotFoundException(book_id));
    }

    public NewEBookResponseDTO save(EBookDTO body){

        if (body.title()== null || body.ISBN()==null )
            throw new ValidationException("Title or ISBN or publication year is required");

        String title = body.title().trim();
        String ISBN = body.ISBN().trim();
        int publication_year = body.publication_year();

        if (title.length()<3 || ISBN.length()<3 || ISBN.length()>13)
            throw new ValidationException("Title or ISBN are of an invalid length");


        EBook newEBook  = new EBook(
                title,
                ISBN,
                body.description(),
                body.publication_year(),
                body.cover_url(),
                body.fileUrl(),
                body.licenseType()
        );

        EBook savedEbook = ebookRepository.save(newEBook);

        return new NewEBookResponseDTO(savedEbook.getBook_id());
    }

    public EBook findEBookByIDAndUpdate(UUID book_id){
        EBook foundEbook =  ebookRepository.findById(book_id).orElseThrow(()->new NotFoundException(book_id));
        return null;
    }
}
