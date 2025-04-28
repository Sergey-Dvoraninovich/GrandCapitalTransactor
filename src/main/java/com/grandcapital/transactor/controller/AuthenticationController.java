package com.grandcapital.transactor.controller;

import com.grandcapital.transactor.DAO.entity.User;
import com.grandcapital.transactor.controller.model.LoginResponse;
import com.grandcapital.transactor.controller.model.LoginUserDto;
import com.grandcapital.transactor.service.IAuthenticationService;
import com.grandcapital.transactor.service.IJwtService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final IJwtService jwtService;
    private final IAuthenticationService authenticationService;

    public AuthenticationController(IJwtService jwtService, IAuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
