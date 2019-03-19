package com.revolut.test.service.impl;

import com.revolut.test.dto.AccountResponse;
import com.revolut.test.exception.NoMoneyOnAccountException;
import com.revolut.test.model.Account;
import com.revolut.test.repository.AccountRepository;
import com.revolut.test.repository.impl.AccountRepositoryImpl;
import com.revolut.test.service.TransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class TransferServiceImpl implements TransferService {

    private static final Logger log = LoggerFactory.getLogger(TransferServiceImpl.class);

    private AccountRepository service = new AccountRepositoryImpl();

    @Override
    public AccountResponse transferMoney(long accountIdFrom, long accountIdTo, BigDecimal amount, String paymentPurpose) {
        log.info("Begin transfer money from account {} to account {} amount of transfer {}", accountIdFrom, accountIdTo, amount);
        AccountResponse response = new AccountResponse();
        Account accountFrom = service.findAccountByNumberWithLock(accountIdFrom);
        Account accountTo = service.findAccountByNumberWithLock(accountIdTo);
        BigDecimal amountFrom = accountFrom.getAmount();
        BigDecimal amountTo = accountTo.getAmount();
        if ((amountFrom.subtract(amount).compareTo(BigDecimal.valueOf(0)) < 0)) {
            log.error("Error when transferring money amount insufficient to transfer");
            throw new NoMoneyOnAccountException("Error when transferring money amount insufficient to transfer." +
                    "You try to transfer money sum: " + amount + " on you account amount: " + amountFrom);
        }
        accountFrom.setAmount(amountFrom.subtract(amount));
        accountTo.setAmount(amountTo.add(amount));
        service.updateAccount(accountFrom);
        service.updateAccount(accountTo);
        response.setAccountAmount(accountFrom.getAmount());
        response.setAccountNumber(accountTo.getId());
        response.setPaymentPurpose(paymentPurpose);
        log.info("Transfer money was success");
        return response;
    }
}
