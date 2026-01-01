package com.giuseppecalvaruso.library365.repositories;

import com.giuseppecalvaruso.library365.entities.Author;
import com.giuseppecalvaruso.library365.entities.EBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EbookRepository extends JpaRepository<EBook, UUID> {

    @Query("""
    SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
    FROM PrintedBook p
    JOIN p.authors a
    WHERE a.id = :author_id
""")
    boolean existsByAuthors_Id(@Param("author_id") UUID author_id);


    Optional<EBook> findByISBNIgnoreCase(String isbn);
    @Query("""
    SELECT DISTINCT e
    FROM EBook e
    LEFT JOIN FETCH e.authors
""")
    List<EBook> findAllWithAuthors();


    @Query("""
        SELECT DISTINCT e
        FROM EBook e
        JOIN e.authors a
        WHERE LOWER(a.firstName) LIKE LOWER(CONCAT('%', :q, '%'))
           OR LOWER(a.lastName)  LIKE LOWER(CONCAT('%', :q, '%'))
    """)
    List<EBook> findByAuthorNameOrSurnameLike(@Param("q") String q);

    @Query("""
        SELECT DISTINCT e
        FROM EBook e
        JOIN e.authors a
        WHERE LOWER(a.firstName) = LOWER(:firstName)
          AND LOWER(a.lastName)  = LOWER(:lastName)
    """)
    List<EBook> findByAuthorFullName(@Param("firstName") String firstName,
                                     @Param("lastName") String lastName);

    List<EBook> findByAuthors_Id(UUID author_id);;
}
