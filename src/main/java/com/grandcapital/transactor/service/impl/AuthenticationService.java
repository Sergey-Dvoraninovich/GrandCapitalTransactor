package com.grandcapital.transactor.service.impl;

import com.grandcapital.transactor.DAO.entity.User;
import com.grandcapital.transactor.controller.model.LoginUserDto;
import com.grandcapital.transactor.service.IAuthenticationService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(
            UserService userService,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public User authenticate(LoginUserDto input) {
        String phoneOrEmail = input.getPhone();
        if (Strings.isBlank(phoneOrEmail)) {
            phoneOrEmail = input.getEmail();
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        phoneOrEmail,
                        input.getPassword()
                )
        );

        return userService.findByPhoneOrEmail(phoneOrEmail).orElseThrow();
    }
}

