package com.grandcapital.transactor.service;

import com.grandcapital.transactor.DAO.AccountRepository;
import com.grandcapital.transactor.DAO.EmailDataRepository;
import com.grandcapital.transactor.DAO.PhoneDataRepository;
import com.grandcapital.transactor.DAO.UsersRepository;
import com.grandcapital.transactor.DAO.entity.Account;
import com.grandcapital.transactor.DAO.entity.EmailData;
import com.grandcapital.transactor.DAO.entity.PhoneData;
import com.grandcapital.transactor.DAO.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DemoDataLoader {

    private UsersRepository usersRepository;
    private PhoneDataRepository phoneDataRepository;
    private EmailDataRepository emailDataRepository;
    private AccountRepository accountRepository;

    DemoDataLoader(UsersRepository userRepository, EmailDataRepository emailRepository,
                   PhoneDataRepository phoneDataRepository, AccountRepository accountRepository) {
        this.usersRepository = userRepository;
        this.phoneDataRepository = phoneDataRepository;
        this.emailDataRepository = emailRepository;
        this.accountRepository = accountRepository;
    }

    @PostConstruct
    public void initializeAfterStartup() {
        if (!usersRepository.findAll().isEmpty()) {
            return;
        }

        User user = new User();
        user.setName("Hans Zimmermann");
        user.setDateOfBirth(LocalDate.of(2002, 2, 2));
        user.setPassword("$2a$10$OvFROh3eYlAxtxL6NO800ORcmwYlWCBHCGv/yhHTslBuJEofxWFCu");
        usersRepository.save(user);

        EmailData emailData = new EmailData();
        emailData.setUser(user);
        emailData.setEmail("correct.email@gmail.com");
        emailDataRepository.save(emailData);

        PhoneData phoneData = new PhoneData();
        phoneData.setUser(user);
        phoneData.setPhone("+375299970202");
        phoneDataRepository.save(phoneData);

        Account account = new Account();
        account.setUser(user);
        account.setBalance(new BigDecimal("1345.44"));
        account.setAutoIncreaseLimit(new BigDecimal("2785.06"));
        accountRepository.save(account);


        User user2 = new User();
        user2.setName("Ingrid Svendsdatter");
        user2.setDateOfBirth(LocalDate.of(2001, 11, 1));
        user2.setPassword("$2a$10$OvFROh3eYlAxtxL6NO800ORcmwYlWCBHCGv/yhHTslBuJEofxWFCu");
        usersRepository.save(user2);

        EmailData emailData2 = new EmailData();
        emailData2.setUser(user2);
        emailData2.setEmail("test@gmail.com");
        emailDataRepository.save(emailData2);

        PhoneData phoneData2 = new PhoneData();
        phoneData2.setUser(user2);
        phoneData2.setPhone("+375299970777");
        phoneDataRepository.save(phoneData2);

        Account account2 = new Account();
        account2.setUser(user2);
        account2.setBalance(new BigDecimal("1345.44"));
        account2.setAutoIncreaseLimit(new BigDecimal("2785.06"));
        accountRepository.save(account2);
    }
}