package com.giuseppecalvaruso.library365.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProfileImageDTO(
        @NotBlank(message = "Profile image is required")
        @Size(max=2048,message = "Image Url is too long")
        String url_pic)
{}
