package com.grandcapital.transactor.service;

import com.grandcapital.transactor.DAO.entity.User;
import com.grandcapital.transactor.controller.model.LoginUserDto;

public interface IAuthenticationService {
    User authenticate(LoginUserDto input);
}
