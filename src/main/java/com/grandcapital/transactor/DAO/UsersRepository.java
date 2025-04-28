package com.grandcapital.transactor.DAO;

import com.grandcapital.transactor.DAO.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
    Optional<User> findByEmailsEmail(String email);
    Optional<User> findByPhonesPhone(String phone);
}
