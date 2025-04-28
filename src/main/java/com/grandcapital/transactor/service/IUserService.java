package com.grandcapital.transactor.service;

import com.grandcapital.transactor.DAO.entity.User;
import com.grandcapital.transactor.service.model.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface IUserService {
    Page<UserDTO> findUsersWithFilters(LocalDate dateOfBirth, String phone, String name, String email, Pageable pageable);

    Optional<User> findByPhoneOrEmail(String phoneOrEmail);

    void putUserEmail(Long id, String email);
    void replaceUserEmail(Long id, String oldEmail, String newEmail);
    void deleteUserEmail(Long id, String email);

    void putUserPhone(Long id, String phone);
    void replaceUserPhone(Long id, String oldPhone, String newPhone);
    void deleteUserPhone(Long id, String phone);
}
