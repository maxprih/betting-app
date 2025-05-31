package com.bebra.betting.services;

import com.bebra.betting.exceptions.AppError;
import com.bebra.betting.models.entity.User;
import com.bebra.betting.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author max_pri
 */
@Service
public class UserRetrievalService {
    private final UserRepository userRepository;

    @Autowired
    public UserRetrievalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        } else {
            String userDetails = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return findByLogin(userDetails).orElseThrow(() -> new AppError(1, "123"));
        }
    }
}
