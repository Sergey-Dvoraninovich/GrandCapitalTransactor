package com.grandcapital.transactor.service.impl;

import com.grandcapital.transactor.DAO.UsersRepository;
import com.grandcapital.transactor.DAO.entity.User;
import com.grandcapital.transactor.exception.MoneyTransferFailedException;
import com.grandcapital.transactor.exception.NotEnoughMoneyException;
import com.grandcapital.transactor.exception.UserNotFoundException;
import com.grandcapital.transactor.service.IMoneyTransferService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MoneyTransferService implements IMoneyTransferService {

    private static final Logger logger = LoggerFactory.getLogger(MoneyTransferService.class);

    private UsersRepository usersRepository;

    MoneyTransferService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void transfer(Long userId, Long targetUserid, BigDecimal transferAmount) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        User targetUser = usersRepository.findById(targetUserid)
                .orElseThrow(() -> new UserNotFoundException(targetUserid));

        if (user.getAccount().getBalance().compareTo(transferAmount) < 0) {
            throw new NotEnoughMoneyException(userId, targetUserid, transferAmount);
        }

        user.getAccount().setBalance(user.getAccount().getBalance().subtract(transferAmount));
        targetUser.getAccount().setBalance(targetUser.getAccount().getBalance().add(transferAmount));
        try {
            usersRepository.save(user);
            usersRepository.save(targetUser);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new MoneyTransferFailedException(userId, targetUserid, transferAmount);
        }

        logger.info(String.format("%s was transferred %d from to %d", transferAmount, userId, targetUserid));
    }
}
