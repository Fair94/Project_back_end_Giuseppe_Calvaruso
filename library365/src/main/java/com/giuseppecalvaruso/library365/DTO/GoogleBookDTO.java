package com.giuseppecalvaruso.library365.DTO;

import java.util.List;

public record GoogleBookDTO(
        String title,
        String isbn,
        Integer publicationYear,
        String description,
        List<String> authors,
        String coverUrl
) {}
