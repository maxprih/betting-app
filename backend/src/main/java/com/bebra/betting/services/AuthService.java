package com.bebra.betting.services;

import com.bebra.betting.exceptions.AppError;
import com.bebra.betting.models.entity.Role;
import com.bebra.betting.models.entity.User;
import com.bebra.betting.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.bebra.dto.RegistrationUserDto;
import org.bebra.dto.RoleDto;
import org.bebra.dto.UserDto;
import org.bebra.dto.requests.JwtRequest;
import org.bebra.dto.responses.SignInResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author max_pri
 */
@Service
@Slf4j
public class AuthService {
    private final UserService userService;
    private final BalanceService balanceService;
    private final UserRetrievalService userRetrievalService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserService userService, BalanceService balanceService, UserRetrievalService userRetrievalService, JwtTokenUtils jwtTokenUtils, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.balanceService = balanceService;
        this.userRetrievalService = userRetrievalService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword())).isAuthenticated();
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getLogin());
        User user = userRetrievalService.findByLogin(userDetails.getUsername()).get();
        String token = jwtTokenUtils.generateToken(userDetails);

        log.info("User {} has been successfully authenticated", user.getLogin());
        return ResponseEntity.ok(new SignInResponse(token, user.getId(), user.getLogin(), user.getName(), balanceService.getBalanceForUser(user).getAmount(), convertToDto(user.getRoles())));
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }
        if (userRetrievalService.findByLogin(registrationUserDto.getLogin()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным логином уже существует"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.createNewUser(registrationUserDto);

        log.info("User {} has been successfully registered", user.getLogin());
        return ResponseEntity.ok(new UserDto(user.getId(), user.getLogin(), user.getName(), balanceService.getBalanceForUser(user).getAmount(), convertToDto(user.getRoles())));
    }

    public UserDto getMe() {
        User user = userRetrievalService.getUserFromContext();

        log.info("User {} has been successfully retrieved", user.getLogin());
        return new UserDto(user.getId(), user.getLogin(), user.getName(), balanceService.getBalanceForUser(user).getAmount(), convertToDto(user.getRoles()));
    }

    private Set<RoleDto> convertToDto(Set<Role> roles) {
        return roles.stream().map(role -> new RoleDto(role.getId(), role.getRoleName())).collect(Collectors.toSet());
    }
}
