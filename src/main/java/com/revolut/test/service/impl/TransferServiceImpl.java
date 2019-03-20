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

    // ура! логгер!
    private static final Logger log = LoggerFactory.getLogger(TransferServiceImpl.class);

    // зачем интерфейс если реализация выбирается и создается тут же?
    private AccountRepository service = new AccountRepositoryImpl();

    @Override
    public AccountResponse transferMoney(long accountIdFrom, long accountIdTo, BigDecimal amount, String paymentPurpose) {
        // почему уровень info?
        log.info("Begin transfer money from account {} to account {} amount of transfer {}", accountIdFrom, accountIdTo, amount);

        AccountResponse response = new AccountResponse();
        // создается первое соединение с БД
        Account accountFrom = service.findAccountByNumberWithLock(accountIdFrom);
        // создается второе соединение с БД
        // распределенной транзакции у тебя тут нет
        Account accountTo = service.findAccountByNumberWithLock(accountIdTo);
        BigDecimal amountFrom = accountFrom.getAmount();
        BigDecimal amountTo = accountTo.getAmount();
        // заменить BigDecimal.valueOf(0) на BigDecimal.ZERO
        // знак определить можно через метод signum()
        if ((amountFrom.subtract(amount).compareTo(BigDecimal.valueOf(0)) < 0)) {
            log.error("Error when transferring money amount insufficient to transfer");
            throw new NoMoneyOnAccountException("Error when transferring money amount insufficient to transfer." +
                    "You try to transfer money sum: " + amount + " on you account amount: " + amountFrom);
        }
        accountFrom.setAmount(amountFrom.subtract(amount));
        accountTo.setAmount(amountTo.add(amount));
        // создается третье соединение с БД
        service.updateAccount(accountFrom);
        // создается четвертое соединение с БД
        service.updateAccount(accountTo);
        // номер аккаунта TO
        // баланс аккаунта FROM
        // так задумано?
        response.setAccountAmount(accountFrom.getAmount());
        response.setAccountNumber(accountTo.getId());
        response.setPaymentPurpose(paymentPurpose);
        log.info("Transfer money was success");
        return response;
    }
}
