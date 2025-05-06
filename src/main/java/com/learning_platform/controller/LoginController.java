package com.learning_platform.controller;

import com.learning_platform.dto.UserDTO;
import com.learning_platform.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> checkLogin(@AuthenticationPrincipal Jwt jwt, @RequestBody UserDTO userDTO) {
        String cognitoUserId = jwt.getClaimAsString("sub");
        UserDTO user = loginService.checkLogin(userDTO, cognitoUserId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body(null); // Unauthorized
        }
    }


}
