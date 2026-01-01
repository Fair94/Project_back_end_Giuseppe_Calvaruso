package com.giuseppecalvaruso.library365.repositories;

import com.giuseppecalvaruso.library365.entities.Author;
import com.giuseppecalvaruso.library365.entities.PrintedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository

public interface PrintedBookRepository extends JpaRepository<PrintedBook, UUID> {

    @Query("""
    SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
    FROM PrintedBook p
    JOIN p.authors a
    WHERE a.id = :author_id
""")
    boolean existsByAuthors_Id(@Param("author_id") UUID author_id);



    Optional<PrintedBook> findByISBNIgnoreCase(String isbn);

    @Query("""
        SELECT DISTINCT p FROM PrintedBook p
        JOIN p.authors a
        WHERE LOWER(a.firstName) LIKE LOWER(CONCAT('%', :q, '%'))
           OR LOWER(a.lastName)  LIKE LOWER(CONCAT('%', :q, '%'))
    """)
    List<PrintedBook> findByAuthorNameOrSurnameLike(@Param("q") String q);

    @Query("""
        SELECT DISTINCT p FROM PrintedBook p
        JOIN p.authors a
        WHERE LOWER(a.firstName) = LOWER(:firstName)
          AND LOWER(a.lastName)  = LOWER(:lastName)
    """)
    List<PrintedBook> findByAuthorFullName(@Param("firstName") String firstName,
                                           @Param("lastName") String lastName);

    List<PrintedBook> findByAuthors_Id(UUID author_id);
    boolean existsByISBNIgnoreCase(String isbn);

}