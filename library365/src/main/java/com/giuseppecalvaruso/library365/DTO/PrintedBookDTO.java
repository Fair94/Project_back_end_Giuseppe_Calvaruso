package com.giuseppecalvaruso.library365.DTO;

public record PrintedBookDTO(String Title,String ISBN, String description,
                             int publication_year,String cover_url,String position,
                             int total_copies,int available_copies) {
}
