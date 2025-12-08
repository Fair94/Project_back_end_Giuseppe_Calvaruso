package DTO;

public record EBookDTO(String title,String ISBN, String description,int publication_year,String cover_url,
                       String fileUrl,String licenseType) {
}
