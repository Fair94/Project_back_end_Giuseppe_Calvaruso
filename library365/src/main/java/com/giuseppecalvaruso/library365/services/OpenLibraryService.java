//package com.giuseppecalvaruso.library365.services;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.giuseppecalvaruso.library365.DTO.OpenLibraryBookDTO;
//import com.giuseppecalvaruso.library365.exceptions.NotFoundException;
//import com.giuseppecalvaruso.library365.exceptions.ValidationException;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Service
//public class OpenLibraryService {
//
//    private final RestTemplate restTemplate = new RestTemplate();
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    private static final Pattern YEAR_PATTERN = Pattern.compile("(\\d{4})");
//
//    public OpenLibraryBookDTO fetchByIsbn(String rawIsbn) {
//        String isbn = normalizeIsbn(rawIsbn);
//        String url = "https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data";
//
//        String json = restTemplate.getForObject(url, String.class);
//
//        try {
//            JsonNode root = objectMapper.readTree(json);
//            String key = "ISBN:" + isbn;
//
//            if (root == null || !root.has(key) || root.get(key).isNull()) {
//                throw new NotFoundException("No OpenLibrary data found for isbn: " + isbn);
//            }
//
//            JsonNode book = root.get(key);
//
//            String title = textOrNull(book, "title");
//            if (title == null || title.isBlank()) {
//                throw new ValidationException("OpenLibrary returned empty title for isbn: " + isbn);
//            }
//
//            String publishDate = textOrNull(book, "publish_date");
//            Integer year = extractYearOrNull(publishDate);
//            if (year == null) {
//                throw new ValidationException("Cannot extract publication year from OpenLibrary publish_date for isbn: " + isbn);
//            }
//
//            // "notes" a volte è testo, a volte oggetto; gestisco solo testo per semplicità
//            String description = null;
//            JsonNode notesNode = book.get("notes");
//            if (notesNode != null && notesNode.isTextual()) {
//                description = notesNode.asText();
//            }
//
//            Integer numberOfPages = intOrNull(book, "number_of_pages");
//
//            List<String> authors = new ArrayList<>();
//            JsonNode authorsNode = book.get("authors");
//            if (authorsNode != null && authorsNode.isArray()) {
//                for (JsonNode a : authorsNode) {
//                    String name = textOrNull(a, "name");
//                    if (name != null) authors.add(name);
//                }
//            }
//
//            List<String> publishers = new ArrayList<>();
//            JsonNode pubNode = book.get("publishers");
//            if (pubNode != null && pubNode.isArray()) {
//                for (JsonNode p : pubNode) {
//                    String name = textOrNull(p, "name");
//                    if (name != null) publishers.add(name);
//                }
//            }
//
//            String coverUrl = null;
//            JsonNode coverNode = book.get("cover");
//            if (coverNode != null && coverNode.isObject()) {
//                coverUrl = textOrNull(coverNode, "large");
//                if (coverUrl == null) coverUrl = textOrNull(coverNode, "medium");
//                if (coverUrl == null) coverUrl = textOrNull(coverNode, "small");
//            }
//
//            // NB: estendo OpenLibraryBookDTO per includere year+description (vedi sotto)
//            return new OpenLibraryBookDTO(
//                    title,
//                    isbn,
//                    authors,
//                    publishers,
//                    publishDate,
//                    year,
//                    truncate(description, 500),
//                    numberOfPages,
//                    coverUrl
//            );
//
//        } catch (RuntimeException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new RuntimeException("Error parsing OpenLibrary response", e);
//        }
//    }
//
//    private String normalizeIsbn(String isbn) {
//        if (isbn == null) return null;
//        return isbn.replaceAll("[^0-9Xx]", "").toUpperCase();
//    }
//
//    private String textOrNull(JsonNode node, String field) {
//        JsonNode v = node.get(field);
//        return (v != null && v.isTextual()) ? v.asText() : null;
//    }
//
//    private Integer intOrNull(JsonNode node, String field) {
//        JsonNode v = node.get(field);
//        return (v != null && v.canConvertToInt()) ? v.asInt() : null;
//    }
//
//    private Integer extractYearOrNull(String publishDate) {
//        if (publishDate == null) return null;
//        Matcher m = YEAR_PATTERN.matcher(publishDate);
//        if (m.find()) {
//            return Integer.parseInt(m.group(1));
//        }
//        return null;
//    }
//
//    private String truncate(String s, int max) {
//        if (s == null) return null;
//        if (s.length() <= max) return s;
//        return s.substring(0, max);
//    }
//}
