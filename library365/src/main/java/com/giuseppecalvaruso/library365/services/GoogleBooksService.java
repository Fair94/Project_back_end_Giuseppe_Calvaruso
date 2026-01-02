package com.giuseppecalvaruso.library365.services;

import com.giuseppecalvaruso.library365.DTO.GoogleBookDTO;
import com.giuseppecalvaruso.library365.exceptions.GoogleNotFoundException;
import com.giuseppecalvaruso.library365.exceptions.NotFoundException;
import com.giuseppecalvaruso.library365.exceptions.ValidationException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GoogleBooksService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final Pattern YEAR_PATTERN = Pattern.compile("(\\d{4})");

    public GoogleBookDTO fetchByIsbn(String rawIsbn) {

        if (rawIsbn == null || rawIsbn.trim().isBlank()) {
            throw new ValidationException("ISBN is required");
        }

        String isbn = normalizeIsbn(rawIsbn);
        if (isbn.length() != 13) {
            throw new ValidationException("ISBN must be 13 characters");
        }

        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn + "&maxResults=1";

        String json = restTemplate.getForObject(url, String.class);
        if (json == null || json.isBlank()) {
            throw new GoogleNotFoundException("Empty response from Google Books");
        }

        JSONObject root = new JSONObject(json);

        JSONArray items = root.optJSONArray("items");
        if (items == null || items.isEmpty()) {
            throw new GoogleNotFoundException("No Google Books data found for isbn: " + isbn);
        }

        JSONObject volumeInfo = items.getJSONObject(0).optJSONObject("volumeInfo");
        if (volumeInfo == null) {
            throw new GoogleNotFoundException("No volumeInfo found for isbn: " + isbn);
        }

        String title = volumeInfo.optString("title", null);
        if (title == null || title.trim().length() < 3) {
            throw new ValidationException("Invalid title from Google Books for isbn: " + isbn);
        }

        String publishedDate = volumeInfo.optString("publishedDate", null);
        Integer year = extractYearOrNull(publishedDate);
        if (year == null) {
            throw new ValidationException("Cannot extract publication year for isbn: " + isbn);
        }

        String description = volumeInfo.optString("description", null);
        description = truncate(description, 500);

        List<String> authors = new ArrayList<>();
        JSONArray authorsArr = volumeInfo.optJSONArray("authors");
        if (authorsArr != null) {
            for (int i = 0; i < authorsArr.length(); i++) {
                String a = authorsArr.optString(i, null);
                if (a != null && !a.isBlank()) authors.add(a);
            }
        }

        String coverUrl = null;
        JSONObject imageLinks = volumeInfo.optJSONObject("imageLinks");
        if (imageLinks != null) {
            coverUrl = imageLinks.optString("thumbnail", null);
            if (coverUrl == null) coverUrl = imageLinks.optString("smallThumbnail", null);
        }

        return new GoogleBookDTO(
                title.trim(),
                isbn,
                year,
                description,
                authors,
                coverUrl
        );
    }

    private String normalizeIsbn(String isbn) {
        return isbn.replaceAll("[^0-9Xx]", "").toUpperCase().trim();
    }

    private Integer extractYearOrNull(String date) {
        if (date == null) return null;
        Matcher m = YEAR_PATTERN.matcher(date);
        return m.find() ? Integer.parseInt(m.group(1)) : null;
    }

    private String truncate(String s, int max) {
        if (s == null) return null;
        return s.length() <= max ? s : s.substring(0, max);
    }
}
