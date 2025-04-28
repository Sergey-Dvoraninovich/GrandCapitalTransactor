package com.grandcapital.transactor.DAO;

import com.grandcapital.transactor.DAO.entity.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailDataRepository extends JpaRepository<EmailData, Long> {

    EmailData save(EmailData emailData);
    Optional<EmailData> findByEmail(String email);
    Optional<EmailData> findByUser_IdAndEmail(Long userId, String email);

}
