package com.learning_platform.service;

import com.learning_platform.dto.UserDTO;
import com.learning_platform.model.User;
import com.learning_platform.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginService loginService;

    @Test
    public void shouldUpdateForExistingUser() {
        // Arrange
        UUID congnitoId = UUID.randomUUID();
        User mockUser = new User();
        mockUser.setEmail("test@email.com");
        UserDTO userDTO = new UserDTO();
        when(userRepository.findByEmail(userDTO.getEmail()))
                .thenReturn(java.util.Optional.of(mockUser))
                .thenReturn(java.util.Optional.of(mockUser));
        // ACT
        loginService.checkLogin(userDTO, congnitoId.toString());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        //ASSERT
        assertNotNull(savedUser.getLastLogin());
        assertNull(savedUser.getCognitoId());
    }

    @Test
    public void shouldCreateForNewUser() {
        // Arrange
        UUID congnitoId = UUID.randomUUID();
        User mockUser = new User();
        mockUser.setEmail("test@email.com");
        UserDTO userDTO = new UserDTO();
        when(userRepository.findByEmail(userDTO.getEmail()))
                .thenReturn(java.util.Optional.empty())
                .thenReturn(java.util.Optional.of(mockUser));
        // ACT
        loginService.checkLogin(userDTO, congnitoId.toString());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        //ASSERT
        assertNotNull(savedUser.getLastLogin());
        assertEquals(savedUser.getCognitoId(), congnitoId);
    }
}
