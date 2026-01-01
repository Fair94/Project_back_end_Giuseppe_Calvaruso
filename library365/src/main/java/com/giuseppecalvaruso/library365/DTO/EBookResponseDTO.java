package com.giuseppecalvaruso.library365.DTO;



import java.util.List;
import java.util.UUID;

public record EBookResponseDTO(
        UUID book_id,
        String title,
        String ISBN,
        String description,
        int publication_year,
        String fileUrl,
        String licenseType,
        List<AuthorDTO> authors
) {}

