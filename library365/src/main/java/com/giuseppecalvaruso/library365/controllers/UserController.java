package com.giuseppecalvaruso.library365.controllers;


import com.giuseppecalvaruso.library365.DTO.NewUserResponseDTO;
import com.giuseppecalvaruso.library365.DTO.UpdateProfileImageDTO;
import com.giuseppecalvaruso.library365.DTO.UserDTO;
import com.giuseppecalvaruso.library365.entities.User;
import com.giuseppecalvaruso.library365.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','SUPERADMIN')")
    @GetMapping
    public List<User> getUsers() {
        return this.userService.getUsers();
    }

    @GetMapping("/{user_id}")
    public User getUser(@PathVariable("user_id") UUID user_id) {
        return this.userService.getUserById(user_id);
    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','SUPERADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponseDTO createUser( @Valid @RequestBody UserDTO body) {
        return this.userService.save(body);
    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','SUPERADMIN')")
    @PutMapping("/{user_id}")
    public User updateUserById(@PathVariable("user_id") UUID user_id, @Valid @RequestBody UserDTO body) {
        return this.userService.findUserByIdAndUpdate(user_id,body);
    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','SUPERADMIN','USER')")
    @PatchMapping(value = "/{user_id}/profile_image", consumes = "multipart/form-data")
    public User updateProfileImage(@PathVariable("user_id") UUID user_id,
                                  @RequestParam("profile_image") MultipartFile profileImage) {
        return this.userService.updateProfileImage(user_id,profileImage);
    }


    @PreAuthorize("hasAnyAuthority('LIBRARIAN','SUPERADMIN')")
    @DeleteMapping("/{user_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable("user_id") UUID user_id) {
        this.userService.findByIdAndDelete(user_id);

    }

}
