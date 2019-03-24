package com.revolut.test.service.impl;

import com.revolut.test.dto.AccountRequest;
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
    public AccountResponse transferMoney(AccountRequest request) {
        log.info("Begin transfer money from account {} to account {} amount of transfer {}",
                request.getAccountNumberFrom(), request.getRecipientAccount(), request.getTransferAmount());
        AccountResponse response = new AccountResponse();
        Account accountFrom = service.findAccountByNumberWithLock(request.getAccountNumberFrom());
        Account accountTo = service.findAccountByNumberWithLock(request.getRecipientAccount());
        BigDecimal amountFrom = accountFrom.getAmount();
        BigDecimal amountTo = accountTo.getAmount();
        BigDecimal transferAmount = request.getTransferAmount();
        if ((amountFrom.subtract(transferAmount).compareTo(BigDecimal.valueOf(0)) < 0)) {
            log.error("Error when transferring money amount insufficient to transfer");
            throw new NoMoneyOnAccountException("Error when transferring money amount insufficient to transfer." +
                    "You try to transfer money sum: " + transferAmount + " on you account amount: " + amountFrom);
        }
        accountFrom.setAmount(amountFrom.subtract(transferAmount));
        accountTo.setAmount(amountTo.add(transferAmount));
        service.updateAccount(accountFrom);
        service.updateAccount(accountTo);
        response.setTransferAmount(request.getTransferAmount());
        response.setAccountNumberFrom(accountFrom.getId());
        response.setAccountAmountFrom(accountFrom.getAmount());
        response.setAccountAmountTo(accountTo.getAmount());
        response.setRecipientAccount(accountTo.getId());
        response.setPaymentPurpose(request.getPaymentPurpose());
        log.info("Transfer money was success");
        return response;
    }
}
