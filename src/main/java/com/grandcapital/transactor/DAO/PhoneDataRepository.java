package com.grandcapital.transactor.DAO;

import com.grandcapital.transactor.DAO.entity.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {
    PhoneData save(PhoneData phoneData);
    Optional<PhoneData> findByPhone(String phone);
    Optional<PhoneData> findByUser_IdAndPhone(Long userId, String phone);
}
