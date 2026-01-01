package com.giuseppecalvaruso.library365.DTO;

import java.util.List;
import java.util.UUID;

public record PrintedBookResponseDTO(
        UUID book_id,
        String title,
        String ISBN,
        String description,
        int publication_year,
        String position,
        int totalCopies,
        int availableCopies,
        List<AuthorDTO> authors
) {}
