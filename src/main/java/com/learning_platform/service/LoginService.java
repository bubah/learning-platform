package com.learning_platform.service;

import com.learning_platform.dto.UserDTO;
import com.learning_platform.model.User;
import com.learning_platform.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LoginService {
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO checkLogin(UserDTO userDTO, String cognitoId) {
        userRepository.findByEmail(userDTO.getEmail())
                .ifPresentOrElse(
                        user -> {

                            user.setLastLogin(LocalDateTime.now());
                            userRepository.save(user);
                        },
                        () -> {
                            User newUser = new User(userDTO);
                            newUser.setLastLogin(LocalDateTime.now());
                            newUser.setCognitoId(UUID.fromString(cognitoId));
                            userRepository.save(newUser);
                        }
                );
        return userRepository.findByEmail(userDTO.getEmail())
                .map(UserDTO::new)
                .orElse(null);
    }
}
