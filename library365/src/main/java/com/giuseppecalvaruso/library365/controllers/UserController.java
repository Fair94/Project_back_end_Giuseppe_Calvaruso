package com.giuseppecalvaruso.library365.controllers;


import com.giuseppecalvaruso.library365.DTO.NewUserResponseDTO;
import com.giuseppecalvaruso.library365.entities.User;
import com.giuseppecalvaruso.library365.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return null;
    }

    @GetMapping("/{user_id}")
    public User getUser(@PathVariable("user_id") UUID user_id) {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponseDTO createUser(@RequestBody NewUserResponseDTO body) {
        return null;
    }

    @PutMapping("{/user_id}")
    public User updateUserById(@PathVariable("user_id") UUID user_id, @RequestBody NewUserResponseDTO body) {
        return null;
    }

    @DeleteMapping("/{user_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable("user_id") UUID user_id) {

    }

}
