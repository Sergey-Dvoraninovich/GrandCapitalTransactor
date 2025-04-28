package com.grandcapital.transactor.controller.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class MoneyTransferRequest {

    @NotBlank(message = "Target user id is required")
    private Long targetUserId;

    @NotBlank(message = "Transfer amount is required")
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = "Value must have at most 2 decimal places")
    private BigDecimal amount;

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
