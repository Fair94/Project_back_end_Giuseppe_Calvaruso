package com.giuseppecalvaruso.library365.services;

import com.giuseppecalvaruso.library365.DTO.AuthorDTO;
import com.giuseppecalvaruso.library365.DTO.NewAuthorResponseDTO;
import com.giuseppecalvaruso.library365.entities.Author;
import com.giuseppecalvaruso.library365.exceptions.NotFoundException;
import com.giuseppecalvaruso.library365.exceptions.ValidationException;
import com.giuseppecalvaruso.library365.repositories.AuthorRepository;
import com.giuseppecalvaruso.library365.repositories.EbookRepository;
import com.giuseppecalvaruso.library365.repositories.PrintedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PrintedBookRepository printedBookRepository;
    @Autowired
    private EbookRepository eBookRepository;

    public List<Author> getAuthors(){
        return authorRepository.findAll();
    }

    public Author getAuthorByID( UUID author_id) {

        return authorRepository.findById(author_id).orElseThrow(()-> new NotFoundException(author_id));

    }

    public NewAuthorResponseDTO save (AuthorDTO body){
        if (body.firstName()==null || body.lastName()==null)
            throw new ValidationException("Missing required fields");

        String firstName = body.firstName().trim();
        String lastName = body.lastName().trim();

        if(firstName.length()<3||lastName.length()<3)
            throw new ValidationException("First name and last name must be of length 3 characters");

        Author newAuthor = new Author(firstName,lastName, body.bio());

        Author authorSaved = authorRepository.save(newAuthor);

        return new NewAuthorResponseDTO(authorSaved.getId());
    }

    public Author findAuthorByID( UUID author_id, AuthorDTO body) {
        Author foundAuthor = authorRepository.findById(author_id).orElseThrow(()-> new NotFoundException(author_id));

        if(body.firstName()!=null) foundAuthor.setFirstName(body.firstName().trim());
        if(body.lastName()!=null) foundAuthor.setLastName(body.lastName().trim());
        if(body.bio()!=null) foundAuthor.setBio(body.bio().trim());

        return this.authorRepository.save(foundAuthor);
    }

    public void findByIDAndDelete(UUID author_id){
        Author foundAuthor = authorRepository.findById(author_id).orElseThrow(()-> new NotFoundException(author_id));
        if (printedBookRepository.existsByAuthors_Id(author_id)
                || eBookRepository.existsByAuthors_Id(author_id)) {
            throw new ValidationException("Cannot delete author: linked to one or more books");
        }
        authorRepository.delete(foundAuthor);
    }
}
