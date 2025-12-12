package com.giuseppecalvaruso.library365.controllers;


import com.giuseppecalvaruso.library365.DTO.NewUserResponseDTO;
import com.giuseppecalvaruso.library365.DTO.UserDTO;
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
        return this.userService.getUsers();
    }

    @GetMapping("/{user_id}")
    public User getUser(@PathVariable("user_id") UUID user_id) {
        return this.userService.getUserById(user_id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponseDTO createUser(@RequestBody UserDTO body) {
        return this.userService.save(body);
    }

    @PutMapping("{/user_id}")
    public User updateUserById(@PathVariable("user_id") UUID user_id, @RequestBody UserDTO body) {
        return this.userService.findUserByIdAndUpdate(user_id,body);
    }

    @DeleteMapping("/{user_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable("user_id") UUID user_id) {
        this.userService.findByIdAndDelete(user_id);

    }

}
