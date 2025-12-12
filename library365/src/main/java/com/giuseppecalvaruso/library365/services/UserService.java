package com.giuseppecalvaruso.library365.services;

import com.giuseppecalvaruso.library365.DTO.NewUserResponseDTO;
import com.giuseppecalvaruso.library365.DTO.UpdateProfileImageDTO;
import com.giuseppecalvaruso.library365.DTO.UserDTO;
import com.giuseppecalvaruso.library365.entities.User;
import com.giuseppecalvaruso.library365.exceptions.NotFoundException;
import com.giuseppecalvaruso.library365.exceptions.ValidationException;
import com.giuseppecalvaruso.library365.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID user_id) {
        return userRepository.findById(user_id).orElseThrow(() -> new NotFoundException(user_id));
    }

    public NewUserResponseDTO save(UserDTO body){
        if(body.firstName().length()<3 || body.lastName().length()<3)
            throw new ValidationException("First Name or Last  must be longer than 3 characters");

        User newUser = new User(body.email(),body.password(),
                                body.firstName(),body.lastName(),
                                body.registration(),body.url_pic());

        User userSaved = userRepository.save(newUser);

        return  new NewUserResponseDTO(userSaved.getId());
    }

    public User findUserByIdAndUpdate(UUID user_id, UserDTO body) {
        User foundUser = userRepository.findById(user_id).orElseThrow(() -> new NotFoundException(user_id));

        foundUser.setFirstName(body.firstName());
        foundUser.setLastName(body.lastName());
        foundUser.setRegistration(body.registration());
        foundUser.setUrl_pic(body.url_pic());
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
}
