package com.learning_platform.controller;

import com.learning_platform.dto.UserDTO;
import com.learning_platform.service.LoginService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {
    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    @Test
    public void shouldCheckLogin() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "subValue");

        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "none");
        Jwt testJwt = new Jwt(
                "mock-token-value",                      // token value
                Instant.now(),                           // issued at
                Instant.now().plusSeconds(3600),         // expires at
                headers,                                 // headers
                claims);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@email.com");

        when(loginService.checkLogin(userDTO, "subValue")).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = loginController.checkLogin(testJwt, userDTO);

        // Assertions
        assert response.getStatusCode().is2xxSuccessful();
        assert response.getBody() != null;
    }

    @Test
    public void shouldCheckLoginReturnNull() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "subValue");

        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "none");
        Jwt testJwt = new Jwt(
                "mock-token-value",                      // token value
                Instant.now(),                           // issued at
                Instant.now().plusSeconds(3600),         // expires at
                headers,                                 // headers
                claims);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@email.com");

        when(loginService.checkLogin(userDTO, "subValue")).thenReturn(null);

        ResponseEntity<UserDTO> response = loginController.checkLogin(testJwt, userDTO);

        // Assertions
        assert response.getStatusCode().is4xxClientError();
        assert response.getBody() == null;
    }
}
