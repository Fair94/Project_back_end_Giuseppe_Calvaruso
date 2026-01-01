package com.giuseppecalvaruso.library365.DTO;

import java.util.List;

public record OpenLibraryBookDTO(
        String title,
        String isbn,
        List<String> authors,
        List<String> publishers,
        String publishDate,
        Integer publicationYear,
        String description,
        Integer numberOfPages,
        String coverUrl
) {}
