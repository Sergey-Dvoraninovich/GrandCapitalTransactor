package com.grandcapital.transactor.exception;

import java.math.BigDecimal;

public class NotEnoughMoneyException extends RuntimeException {

    public NotEnoughMoneyException(Long userId, Long targetUserId, BigDecimal amount) {
        super(String.format("Not enough money to make a transaction from %d to %d for %s", userId, targetUserId, amount.toPlainString()));
    }

}
