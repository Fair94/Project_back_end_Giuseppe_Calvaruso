package com.giuseppecalvaruso.library365.controllers;

import com.giuseppecalvaruso.library365.DTO.AuthResponse;
import com.giuseppecalvaruso.library365.DTO.LoginDTO;
import com.giuseppecalvaruso.library365.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginDTO body){
        return authService.authenticate(body);
    }
}
