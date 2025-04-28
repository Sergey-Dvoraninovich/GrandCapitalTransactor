package com.grandcapital.transactor.service;

import java.math.BigDecimal;

public interface IMoneyTransferService {
    void transfer(Long userId, Long targetUserid, BigDecimal transferAmount);
}
