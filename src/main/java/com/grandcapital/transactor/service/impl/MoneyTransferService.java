package com.grandcapital.transactor.service.impl;

import com.grandcapital.transactor.DAO.AccountRepository;
import com.grandcapital.transactor.DAO.UsersRepository;
import com.grandcapital.transactor.DAO.entity.Account;
import com.grandcapital.transactor.DAO.entity.User;
import com.grandcapital.transactor.exception.NotEnoughMoneyException;
import com.grandcapital.transactor.exception.UserNotFoundException;
import com.grandcapital.transactor.service.IMoneyTransferService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class MoneyTransferService implements IMoneyTransferService {

    private static final Logger logger = LoggerFactory.getLogger(MoneyTransferService.class);

    private UsersRepository usersRepository;
    private AccountRepository accountRepository;

    MoneyTransferService(UsersRepository usersRepository, AccountRepository accountRepository) {
        this.usersRepository = usersRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void transfer(Long userId, Long targetUserid, BigDecimal transferAmount) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        User targetUser = usersRepository.findById(targetUserid)
                .orElseThrow(() -> new UserNotFoundException(targetUserid));

        if (user.getAccount().getBalance().compareTo(transferAmount) < 0) {
            throw new NotEnoughMoneyException(userId, targetUserid, transferAmount);
        }

        Account userAccount = accountRepository.findByUserId(userId);
        Account targetUserAccount = accountRepository.findByUserId(targetUserid);
        userAccount.setBalance(userAccount.getBalance().subtract(transferAmount));
        targetUserAccount.setBalance(targetUserAccount.getBalance().add(transferAmount));
        accountRepository.save(userAccount);
        accountRepository.save(targetUserAccount);

        logger.info(String.format("%s was transferred %d from to %d", transferAmount, userId, targetUserid));
    }
}
