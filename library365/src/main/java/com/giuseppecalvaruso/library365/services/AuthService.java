package com.giuseppecalvaruso.library365.services;

import com.giuseppecalvaruso.library365.DTO.AuthResponse;
import com.giuseppecalvaruso.library365.DTO.LoginDTO;
import com.giuseppecalvaruso.library365.entities.User;
import com.giuseppecalvaruso.library365.exceptions.UnauthorizedException;
import com.giuseppecalvaruso.library365.repositories.UserRepository;
import com.giuseppecalvaruso.library365.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder passwordEncoder;

public AuthResponse authenticate(LoginDTO body){
    User user = userRepository.findByEmail(body.email()).orElseThrow(()->new UnauthorizedException("Credenziali errate"));

    if(!passwordEncoder.matches(body.password(),user.getPassword())){
        throw new UnauthorizedException("Credenziali errate");
    }

    String token = jwtTools.createToken(user);

    return new AuthResponse(token);

}


}
