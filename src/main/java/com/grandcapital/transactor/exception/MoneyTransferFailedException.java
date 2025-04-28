package com.grandcapital.transactor.exception;

import java.math.BigDecimal;

public class MoneyTransferFailedException extends RuntimeException {

    public MoneyTransferFailedException(Long userId, Long targetUserId, BigDecimal amount) {
        super(String.format("Transfer from user %d to user %d for %s failed. Try again", userId, targetUserId, amount.toPlainString()));
    }

}
