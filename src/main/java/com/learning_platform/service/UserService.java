package com.learning_platform.service;

import com.learning_platform.dto.UserDTO;
import com.learning_platform.model.User;
import com.learning_platform.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserDTO userDTO){
        User user = new User(userDTO);
        User savedUser = userRepository.save(user);

        return new UserDTO(savedUser);
    }


}
