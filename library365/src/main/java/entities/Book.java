package entities;

import jakarta.persistence.*;

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

    @Column(name="ISBN", nullable = false)
    private String ISBN;

    @Column(name="description")
    private String description;

    @Column(name ="publication_year")
    private int publication_year;

    @Column(name="cover_url")
    private String cover_url;

    public Book(String title, String ISBN, String description, int publication_year, String cover_url) {
        this.title = title;
        this.ISBN = ISBN;
        this.description = description;
        this.publication_year = publication_year;
        this.cover_url = cover_url;
    }

    public Book() {

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

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", cover_url='" + cover_url + '\'' +
                ", publication_year=" + publication_year +
                ", description='" + description + '\'' +
                ", ISBN='" + ISBN + '\'' +
                '}';
    }
}
