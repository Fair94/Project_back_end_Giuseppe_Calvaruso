package com.giuseppecalvaruso.library365.services;

import com.giuseppecalvaruso.library365.DTO.EBookDTO;
import com.giuseppecalvaruso.library365.DTO.NewEBookResponseDTO;
import com.giuseppecalvaruso.library365.entities.Author;
import com.giuseppecalvaruso.library365.entities.Book;
import com.giuseppecalvaruso.library365.entities.EBook;
import com.giuseppecalvaruso.library365.exceptions.EBookNotFoundException;
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
    @Autowired
    private AuthorService authorService;


    public List<EBook> getAllEBooks(){
        return ebookRepository.findAllWithAuthors();
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

        if (title.length() < 3 || ISBN.length() != 13)
            throw new ValidationException("Title must be at least 3 characters and ISBN must be 13 characters");



        EBook newEBook  = new EBook(
                title,
                ISBN,
                body.description(),
                body.publication_year(),
                body.fileUrl(),
                body.licenseType()
        );

        List<Author> authors = body.authorIds().stream()
                .map(authorService::findAuthorByID)
                .toList();

        newEBook.setAuthors(authors);


        EBook savedEbook = ebookRepository.save(newEBook);

        return new NewEBookResponseDTO(savedEbook.getBook_id());
    }

    public EBook findEBookByIDAndUpdate(UUID book_id, EBookDTO body){
        EBook foundEbook =  ebookRepository.findById(book_id).orElseThrow(()->new NotFoundException(book_id));

        if (body.title()!= null ) foundEbook.setTitle(body.title());
        if (body.ISBN()!= null) foundEbook.setISBN(body.ISBN());
        if (body.description()!= null) foundEbook.setDescription(body.description());



        if (body.fileUrl() != null) {
            String url = body.fileUrl().trim();
            if (url.isBlank()) throw new ValidationException("File url cannot be blank");
            foundEbook.setFileUrl(url);
        }
        if(body.licenseType()!= null) foundEbook.setLicenseType(body.licenseType());

        return this.ebookRepository.save(foundEbook);

    }

    public void findByIdAndDelete(UUID book_id){
        EBook foundEbook =  ebookRepository.findById(book_id).orElseThrow(()->new NotFoundException(book_id));
        this.ebookRepository.delete(foundEbook);
    }



    public EBook updateEbookUrlFile(UUID book_id, String file_url){
        if (file_url==null || file_url.trim().isBlank() ){
            throw new ValidationException("File url is required");
        }
        EBook ebook = this.getEBookById(book_id);
        String normalized_url = file_url.trim();
        ebook.setFileUrl(normalized_url);
        return this.ebookRepository.save(ebook);
    }

    public List <EBook> getEBookByAuthor(String nameSurnameAuthor){
        if(nameSurnameAuthor==null || nameSurnameAuthor.trim().isBlank()){
            throw new ValidationException("Author name or Surname are required");

        }


        return ebookRepository.findByAuthorNameOrSurnameLike(nameSurnameAuthor.trim());

    }

    public List<EBook> getEbookByAuthorFullName(String firstName, String lastName){
        if(firstName==null || lastName==null || lastName.trim().isBlank() || firstName.trim().isBlank()){
            throw new ValidationException("Author name or Surname are required");
        }
        return ebookRepository.findByAuthorFullName(firstName.trim(), lastName.trim());
    }

    public EBook getEBookByISBN(String ISBN){
        if (ISBN == null || ISBN.trim().isBlank()){
            throw new ValidationException("ISBN  is required");
        }

        String normalized_ISBN = ISBN.trim();

        return ebookRepository.findByISBNIgnoreCase(normalized_ISBN).orElseThrow(()-> new EBookNotFoundException(ISBN));
    }

    public List<EBook> findByAuthor(UUID author_id){
        return ebookRepository.findByAuthors_Id(author_id);
    }



}
