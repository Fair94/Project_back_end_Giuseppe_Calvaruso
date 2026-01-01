package com.giuseppecalvaruso.library365.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "book")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Book {
    @Id
    @GeneratedValue
    @Column(name="book_id")
    private UUID book_id;

    @Column(name ="title", nullable = false)
    private String title;

    @Column(name="ISBN", nullable = false, unique = true)
    private String ISBN;

    @Column(name="description", length=1000)
    private String description;

    @Column(name ="publication_year", nullable = false)
    private int publication_year;




    @ManyToMany(fetch = FetchType.EAGER)

    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name="book_id"),
            inverseJoinColumns = @JoinColumn(name="author_id")
    )
    private List<Author> authors = new ArrayList<>();


    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Book(String title, String ISBN, String description, int publication_year) {
        this.title = title;
        this.ISBN = ISBN;
        this.description = description;
        this.publication_year = publication_year;

    }

    public Book() {

    }
    public UUID getBook_id() {
        return book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(int publication_year) {
        this.publication_year = publication_year;
    }





    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id +
                ", title='" + title + '\'' +
                ", publication_year=" + publication_year +
                ", description='" + description + '\'' +
                ", ISBN='" + ISBN + '\'' +
                '}';
    }



}
