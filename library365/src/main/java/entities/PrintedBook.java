package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "printed_book")
public class PrintedBook extends Book {

    @Column (name ="position")
    private String position;

    @Column(name = "total_copies")
    private int totalCopies;

    @Column(name = "available_copies")
    private int availableCopies;


    public PrintedBook(String title, String ISBN, String description, int publication_year, String cover_url, String position, int totalCopies, int availableCopies) {
        super(title, ISBN, description, publication_year, cover_url);
        this.position = position;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public PrintedBook() {

    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    @Override
    public String toString() {
        return "PrintedBook{" +
                "position='" + position + '\'' +
                ", totalCopies=" + totalCopies +
                ", availableCopies=" + availableCopies +
                '}';
    }
}
