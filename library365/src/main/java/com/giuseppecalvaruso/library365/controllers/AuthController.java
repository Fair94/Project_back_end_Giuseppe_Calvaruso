package com.giuseppecalvaruso.library365.controllers;

import com.giuseppecalvaruso.library365.DTO.AuthResponse;
import com.giuseppecalvaruso.library365.DTO.LoginDTO;
import com.giuseppecalvaruso.library365.DTO.NewUserResponseDTO;
import com.giuseppecalvaruso.library365.DTO.UserDTO;
import com.giuseppecalvaruso.library365.services.AuthService;
import com.giuseppecalvaruso.library365.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginDTO body){
        return authService.authenticate(body);
    }

    
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponseDTO register(@Valid @RequestBody UserDTO body) {
        return userService.save(body);
    }



}
