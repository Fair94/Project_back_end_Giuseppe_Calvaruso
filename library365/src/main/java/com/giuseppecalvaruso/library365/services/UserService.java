package com.giuseppecalvaruso.library365.services;

import com.giuseppecalvaruso.library365.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
}
