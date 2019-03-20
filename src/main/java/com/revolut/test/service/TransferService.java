package com.revolut.test.service;

import com.revolut.test.dto.AccountRequest;
import com.revolut.test.dto.AccountResponse;
import com.revolut.test.model.Account;

import java.math.BigDecimal;

public interface TransferService {
    AccountResponse transferMoney(AccountRequest request);
}
