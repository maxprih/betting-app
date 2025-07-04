package com.bebra.betting.controllers;

import com.bebra.betting.services.AuthService;
import org.bebra.dto.RegistrationUserDto;
import org.bebra.dto.UserDto;
import org.bebra.dto.requests.JwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author max_pri
 */
@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin(origins = "http://localhost:8081")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe() {
        return ResponseEntity.ok(authService.getMe());
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }
}
