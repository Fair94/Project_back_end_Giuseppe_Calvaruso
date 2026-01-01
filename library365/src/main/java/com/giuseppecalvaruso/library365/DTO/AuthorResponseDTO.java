package com.giuseppecalvaruso.library365.DTO;

import java.util.UUID;

public record AuthorResponseDTO(
        UUID author_id,
        String firstName,
        String lastName,
        String bio
) {}
