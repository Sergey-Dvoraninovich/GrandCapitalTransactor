package com.grandcapital.transactor.controller;

import com.grandcapital.transactor.controller.model.MoneyTransferRequest;
import com.grandcapital.transactor.service.impl.MoneyTransferService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springframework.http.HttpStatus.*;

@RestController
public class MoneyTransferController {

    private MoneyTransferService moneyTransferService;

    MoneyTransferController(MoneyTransferService moneyTransferService) {
        this.moneyTransferService = moneyTransferService;
    }

    @PutMapping("/transfer")
    @Transactional
    public ResponseEntity transfer(@RequestBody @Valid MoneyTransferRequest request, Principal principal) {
        Long userId = Long.valueOf(principal.getName());
        moneyTransferService.transfer(userId, request.getTargetUserId(), request.getAmount());
        return new ResponseEntity<>(OK);
    }

}
