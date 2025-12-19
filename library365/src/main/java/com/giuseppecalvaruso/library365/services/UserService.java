package com.giuseppecalvaruso.library365.services;

import com.giuseppecalvaruso.library365.DTO.NewUserResponseDTO;
import com.giuseppecalvaruso.library365.DTO.UpdateProfileImageDTO;
import com.giuseppecalvaruso.library365.DTO.UserDTO;
import com.giuseppecalvaruso.library365.entities.Role;
import com.giuseppecalvaruso.library365.entities.User;
import com.giuseppecalvaruso.library365.exceptions.NotFoundException;
import com.giuseppecalvaruso.library365.exceptions.ValidationException;
import com.giuseppecalvaruso.library365.repositories.RoleRepository;
import com.giuseppecalvaruso.library365.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(   UUID user_id) {
        return userRepository.findById(user_id).orElseThrow(() -> new NotFoundException(user_id));
    }

    public NewUserResponseDTO save(UserDTO body){
        if(body.firstName() ==null || body.lastName() ==null || body.email() ==null || body.password() ==null)
            throw new ValidationException("Missing required fields");

        String firstName = body.firstName().trim();
        String lastName = body.lastName().trim();
        String email = body.email().trim();
        String urlPic = (body.url_pic()== null ||body.url_pic().isBlank()) ? null : body.url_pic().trim();

        if (firstName.length()<3||lastName.length()<3)
            throw new ValidationException("First name and last name must be of length 3 characters");

        User newUser = new User(email,body.password(),
                                firstName,lastName,
                                LocalDateTime.now(),
                                urlPic);

        Role defaultRole = roleRepository.findByName("USER")
                .orElseGet(()-> roleRepository.save(new Role("USER")));

        newUser.getRoles().add(defaultRole);
        User userSaved = userRepository.save(newUser);

        return  new NewUserResponseDTO(userSaved.getId());
    }

    public User findUserByIdAndUpdate(UUID user_id, UserDTO body) {
        User foundUser = userRepository.findById(user_id).orElseThrow(() -> new NotFoundException(user_id));


        if(body.firstName()!= null) foundUser.setFirstName(body.firstName());
        if(body.lastName()!=null) foundUser.setLastName(body.lastName());

        foundUser.setRegistration(body.registration());
        if(body.url_pic()!= null){
            String urlPic = body.url_pic().trim();
            foundUser.setUrl_pic(urlPic.isBlank()? null:urlPic);

        }
        foundUser.setEmail(body.email());
        foundUser.setPassword(body.password());

        return this.userRepository.save(foundUser);
    }

    public void findByIdAndDelete(UUID user_id) {
        User foundUser = userRepository.findById(user_id).orElseThrow(() -> new NotFoundException(user_id));
        this.userRepository.delete(foundUser);
    }


    public User updateProfileImage(UUID user_id, String url_pic) {
        User user = this.getUserById(user_id);
        String normalizedUrl = url_pic.trim();
        user.setUrl_pic(normalizedUrl);
        return this.userRepository.save(user);
    }

    public User getUserByIdWithRoles(UUID user_id) {
        return userRepository.findById(user_id).orElseThrow(() -> new NotFoundException(user_id));
    }
}
