package com.bebra.betting.services;

import com.bebra.betting.models.entity.User;
import com.bebra.betting.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bebra.dto.RegistrationUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author max_pri
 */
@Service
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserRetrievalService userRetrievalService;
    private final RoleService roleService;
    private final BalanceService balanceService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserRetrievalService userRetrievalService, RoleService roleService, BalanceService balanceService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRetrievalService = userRetrievalService;
        this.roleService = roleService;
        this.balanceService = balanceService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRetrievalService.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", login)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList())
        );
    }

    @Transactional
    public User createNewUser(RegistrationUserDto registrationUserDto) {
        User user = new User();

        user.setLogin(registrationUserDto.getLogin());
        user.setName(registrationUserDto.getName());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRoles(Set.of(roleService.getUserRole()));

        user = userRepository.save(user);

        balanceService.createBalanceForUser(user);
        log.info("User {} has been successfully created", user.getLogin());
        return user;
    }
}
