package com.revolut.test.service;

import com.revolut.test.dto.AccountResponse;

import java.math.BigDecimal;

public interface TransferService {
    AccountResponse transferMoney(long accountFrom, long accountTo, BigDecimal amount, String paymentPurpose);
}
