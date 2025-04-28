package com.grandcapital.transactor.service.impl;

import com.grandcapital.transactor.DAO.EmailDataRepository;
import com.grandcapital.transactor.DAO.PhoneDataRepository;
import com.grandcapital.transactor.DAO.UsersRepository;
import com.grandcapital.transactor.DAO.UsersSearchRepository;
import com.grandcapital.transactor.DAO.entity.EmailData;
import com.grandcapital.transactor.DAO.entity.PhoneData;
import com.grandcapital.transactor.DAO.entity.User;
import com.grandcapital.transactor.exception.*;
import com.grandcapital.transactor.service.IUserService;
import com.grandcapital.transactor.service.model.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private UsersRepository usersRepository;
    private UsersSearchRepository usersSearchRepository;
    private EmailDataRepository emailDataRepository;
    private PhoneDataRepository phoneDataRepository;
    private ModelMapper modelMapper;

    UserService(UsersRepository usersRepository, UsersSearchRepository usersSearchRepository,
                EmailDataRepository emailDataRepository, PhoneDataRepository phoneDataRepository,
                @Qualifier("advancedModelMapper") ModelMapper modelMapper) {
        this.usersRepository = usersRepository;
        this.usersSearchRepository = usersSearchRepository;
        this.emailDataRepository = emailDataRepository;
        this.phoneDataRepository = phoneDataRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Cacheable(value = "users", key = "{#dateOfBirth, #phone, #name, #email, #page, #size, #pageable}")
    public Page<UserDTO> findUsersWithFilters(LocalDate dateOfBirth, String phone, String name, String email, Pageable pageable) {
        List<User> users = usersSearchRepository.findUsersWithFilters(dateOfBirth, phone, name, email, pageable);
        long total = usersSearchRepository.countUsersWithFilters(dateOfBirth, phone, name, email);
        List<UserDTO> usersDTO = users.stream().map(user -> modelMapper.map(user, UserDTO.class)).toList();
        return new PageImpl<>(usersDTO, pageable, total);
    }

    @Override
    public Optional<User> findByPhoneOrEmail(String phoneOrEmail) {
        Optional<User> user = usersRepository.findByPhonesPhone(phoneOrEmail);
        if (user.isEmpty()) {
            user = usersRepository.findByEmailsEmail(phoneOrEmail);
        }
        return user;
    }
    
    @Override
    public void putUserEmail(Long id, String email) {
        if (usersRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException(id);
        }
        if (emailDataRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException(id, email);
        }
        EmailData emailData = new EmailData();
        emailData.setUser(usersRepository.findById(id).get());
        emailData.setEmail(email);
        emailDataRepository.save(emailData);

        logger.info(String.format("Added email %s for user with id %d", email, id));
    }

    @Override
    public void replaceUserEmail(Long id, String oldEmail, String newEmail) {
        if (usersRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException(id);
        }
        if (emailDataRepository.findByUser_IdAndEmail(id, oldEmail).isEmpty()) {
            throw new EmailNotFoundException(id, oldEmail);
        }
        EmailData emailData = emailDataRepository.findByUser_IdAndEmail(id, oldEmail).get();
        emailData.setEmail(newEmail);
        emailDataRepository.save(emailData);

        logger.info(String.format("Replaced email %s with %s for user with id %d", oldEmail, newEmail, id));
    }

    @Override
    public void deleteUserEmail(Long id, String email) {
        if (usersRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException(id);
        }
        if (emailDataRepository.findByUser_IdAndEmail(id, email).isEmpty()) {
            throw new EmailNotFoundException(id, email);
        }
        EmailData emailData = emailDataRepository.findByUser_IdAndEmail(id, email).get();
        emailDataRepository.delete(emailData);

        logger.info(String.format("Deleted email %s for user with id %d", email, id));
    }

    @Override
    public void putUserPhone(Long id, String phone) {
        if (usersRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException(id);
        }
        if (phoneDataRepository.findByPhone(phone).isPresent()) {
            throw new PhoneAlreadyExistsException(id, phone);
        }
        PhoneData phoneData = new PhoneData();
        phoneData.setUser(usersRepository.findById(id).get());
        phoneData.setPhone(phone);
        phoneDataRepository.save(phoneData);

        logger.info(String.format("Added phone %s for user with id %d", phone, id));
    }

    @Override
    public void replaceUserPhone(Long id, String oldPhone, String newPhone) {
        if (usersRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException(id);
        }
        if (phoneDataRepository.findByUser_IdAndPhone(id, oldPhone).isEmpty()) {
            throw new PhoneNotFoundException(id, oldPhone);
        }
        PhoneData phoneData = phoneDataRepository.findByUser_IdAndPhone(id, oldPhone).get();
        phoneData.setPhone(newPhone);
        phoneDataRepository.save(phoneData);

        logger.info(String.format("Replaced phone %s with %s for user with id %d", oldPhone, newPhone, id));
    }

    @Override
    public void deleteUserPhone(Long id, String phone) {
        if (usersRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException(id);
        }
        if (phoneDataRepository.findByUser_IdAndPhone(id, phone).isEmpty()) {
            throw new PhoneNotFoundException(id, phone);
        }
        PhoneData phoneData = phoneDataRepository.findByUser_IdAndPhone(id, phone).get();
        phoneDataRepository.delete(phoneData);

        logger.info(String.format("Deleted phone %s for user with id %d", phone, id));
    }
}
