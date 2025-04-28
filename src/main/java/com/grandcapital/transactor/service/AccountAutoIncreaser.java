package com.grandcapital.transactor.service;

import com.grandcapital.transactor.DAO.AccountRepository;
import com.grandcapital.transactor.DAO.entity.Account;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class AccountAutoIncreaser {

    private static final Logger logger = LoggerFactory.getLogger(AccountAutoIncreaser.class);

    AccountRepository accountRepository;

    AccountAutoIncreaser(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Scheduled(fixedRate = 30000)
    public void increaseBalance() {
        List<Account> accounts = accountRepository.findAccountsWithBalanceLessThanLimit();
        for (Account account : accounts) {
            BigDecimal balance = account.getBalance().multiply(new BigDecimal("1.10")).setScale(2, RoundingMode.HALF_DOWN);
            balance = balance.compareTo(account.getAutoIncreaseLimit()) >= 0 ? account.getAutoIncreaseLimit() : balance;
            account.setBalance(balance);
        }
        accountRepository.saveAll(accounts);

        logger.info(String.format("Balance of the %d users was increased", accounts.size()));
    }
}
