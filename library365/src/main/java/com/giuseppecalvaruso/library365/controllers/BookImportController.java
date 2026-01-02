package com.giuseppecalvaruso.library365.controllers;

import com.giuseppecalvaruso.library365.DTO.*;
import com.giuseppecalvaruso.library365.services.BookImportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/import")
@PreAuthorize("hasAnyAuthority('SUPERADMIN')")
public class BookImportController {

    @Autowired
    private BookImportService bookImportService;

    @PostMapping("/ebook/isbn")
    public NewEBookResponseDTO importEBook(@RequestBody @Valid ImportEBookByIsbnDTO body) {
        return bookImportService.importEBookByIsbn(body);
    }

    @PostMapping("/printed/isbn")
    public NewPrintedBookResponseDTO importPrinted(@RequestBody @Valid ImportPrintedBookByIsbnDTO body) {
        return bookImportService.importPrintedBookByIsbn(body);
    }
}
