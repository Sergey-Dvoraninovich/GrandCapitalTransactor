package com.grandcapital.transactor.service;

import com.grandcapital.transactor.DAO.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {

    Long extractUserId(String token);

    String generateToken(User user);

    long getExpirationTime();

    boolean isTokenValid(String token, UserDetails userDetails);
}
