package com.giuseppecalvaruso.library365.services;

import com.giuseppecalvaruso.library365.API.CloudinaryService;
import com.giuseppecalvaruso.library365.DTO.NewUserResponseDTO;
import com.giuseppecalvaruso.library365.DTO.UserDTO;
import com.giuseppecalvaruso.library365.entities.Role;
import com.giuseppecalvaruso.library365.entities.User;
import com.giuseppecalvaruso.library365.exceptions.NotFoundException;
import com.giuseppecalvaruso.library365.exceptions.ValidationException;
import com.giuseppecalvaruso.library365.repositories.RoleRepository;
import com.giuseppecalvaruso.library365.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CloudinaryService cloudinaryService;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(   UUID user_id) {
        return userRepository.findById(user_id).orElseThrow(() -> new NotFoundException(user_id));
    }

    public NewUserResponseDTO save(UserDTO body){
        if(body.firstName() ==null || body.lastName() ==null || body.email() ==null || body.password() ==null)
            throw new ValidationException("Missing required fields");

        if (body.user_id() != null)
            throw new ValidationException("user_id must not be provided");


        if (body.registration() != null)
            throw new ValidationException("registration must not be provided");


        String firstName = body.firstName().trim();
        String lastName = body.lastName().trim();
        String email = body.email().trim().toLowerCase();
        String urlPic = (body.url_pic()== null ||body.url_pic().isBlank()) ? null : body.url_pic().trim();



        if (firstName.length()<3||lastName.length()<3)
            throw new ValidationException("First name and last name must be of length 3 characters");

        String encodedPassword = passwordEncoder.encode(body.password());
        if (userRepository.existsEmail(email))
            throw new ValidationException("Email already in use");

        User newUser = new User(email,
                                encodedPassword,
                                firstName,lastName,
                                LocalDateTime.now(),
                                urlPic);


        if (email.equals("superadmin@library365.it")) {
            newUser.getRoles().add(getOrCreateRole("SUPERADMIN"));

        } else if (email.endsWith("@library365.it")) {
            newUser.getRoles().add(getOrCreateRole("LIBRARIAN"));

        } else {
            newUser.getRoles().add(getOrCreateRole("USER"));
        }




        User userSaved = userRepository.save(newUser);
        return  new NewUserResponseDTO(userSaved.getId());
    }

    public User findUserByIdAndUpdate(UUID user_id, UserDTO body) {
        User foundUser = userRepository.findById(user_id)
                .orElseThrow(() -> new NotFoundException(user_id));

        if (body.firstName() != null) foundUser.setFirstName(body.firstName().trim());
        if (body.lastName() != null) foundUser.setLastName(body.lastName().trim());

        if (body.email() != null) foundUser.setEmail(body.email().trim().toLowerCase());

        if (body.password() != null && !body.password().isBlank())
            foundUser.setPassword(passwordEncoder.encode(body.password()));



        return this.userRepository.save(foundUser);
    }


    public void findByIdAndDelete(UUID user_id) {
        User foundUser = userRepository.findById(user_id).orElseThrow(() -> new NotFoundException(user_id));
        this.userRepository.delete(foundUser);
    }


    public NewUserResponseDTO updateProfileImage(UUID user_id, MultipartFile file) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new NotFoundException(user_id));

        String imageUrl = cloudinaryService.uploadImage(file);
        user.setUrl_pic(imageUrl);

        userRepository.save(user);
        return new NewUserResponseDTO(user.getId());
    }


    public User getUserByIdWithRoles(UUID user_id) {
        return userRepository.findIdWithRoles(user_id).orElseThrow(() -> new NotFoundException(user_id));
    }


    private Role getOrCreateRole(String roleName){
        return roleRepository.findByName(roleName)
                .orElseGet(()-> roleRepository.save(new Role(roleName)));
    }
}
