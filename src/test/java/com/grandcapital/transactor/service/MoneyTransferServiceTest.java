package com.grandcapital.transactor.service;

import com.grandcapital.transactor.DAO.AccountRepository;
import com.grandcapital.transactor.DAO.UsersRepository;
import com.grandcapital.transactor.DAO.entity.Account;
import com.grandcapital.transactor.DAO.entity.User;
import com.grandcapital.transactor.exception.NotEnoughMoneyException;
import com.grandcapital.transactor.exception.UserNotFoundException;
import com.grandcapital.transactor.service.impl.MoneyTransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MoneyTransferServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private MoneyTransferService moneyTransferService;

    @Test
    void transferSuccessful() {
        Long userId = 1L;
        Long targetUserId = 2L;
        BigDecimal transferAmount = new BigDecimal("50.00");

        User user = new User();
        user.setId(userId);
        Account userAccount = new Account();
        userAccount.setBalance(new BigDecimal("100.00"));
        user.setAccount(userAccount);

        User targetUser = new User();
        targetUser.setId(targetUserId);
        Account targetAccount = new Account();
        targetAccount.setBalance(new BigDecimal("20.00"));
        targetUser.setAccount(targetAccount);

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(usersRepository.findById(targetUserId)).thenReturn(Optional.of(targetUser));
        when(accountRepository.findByUserId(userId)).thenReturn(userAccount);
        when(accountRepository.findByUserId(targetUserId)).thenReturn(targetAccount);

        moneyTransferService.transfer(userId, targetUserId, transferAmount);

        assertEquals(new BigDecimal("50.00"), user.getAccount().getBalance());
        assertEquals(new BigDecimal("70.00"), targetUser.getAccount().getBalance());
    }

    @Test
    void transferUserNotFound() {
        Long userId = 1L;
        Long targetUserId = 2L;
        BigDecimal transferAmount = new BigDecimal("50.00");

        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> moneyTransferService.transfer(userId, targetUserId, transferAmount));
    }

    @Test
    void transferTargetUserNotFound() {
        // Arrange
        Long userId = 1L;
        Long targetUserId = 2L;
        BigDecimal transferAmount = new BigDecimal("50.00");

        User user = new User();
        user.setId(userId);
        Account userAccount = new Account();
        userAccount.setBalance(new BigDecimal("100.00"));
        user.setAccount(userAccount);

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(usersRepository.findById(targetUserId)).thenReturn(Optional.empty());


        assertThrows(UserNotFoundException.class, () -> moneyTransferService.transfer(userId, targetUserId, transferAmount));
    }

    @Test
    void transferNotEnoughMoney() {
        Long userId = 1L;
        Long targetUserId = 2L;
        BigDecimal transferAmount = new BigDecimal("150.00");

        User user = new User();
        user.setId(userId);
        Account userAccount = new Account();
        userAccount.setBalance(new BigDecimal("20.00"));
        user.setAccount(userAccount);

        User targetUser = new User();
        targetUser.setId(targetUserId);
        Account targetAccount = new Account();
        targetAccount.setBalance(new BigDecimal("100.00"));
        targetUser.setAccount(targetAccount);

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(usersRepository.findById(targetUserId)).thenReturn(Optional.of(targetUser));

        assertThrows(NotEnoughMoneyException.class, () -> moneyTransferService.transfer(userId, targetUserId, transferAmount));
    }
}
