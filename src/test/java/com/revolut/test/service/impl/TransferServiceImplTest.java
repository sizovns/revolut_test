package com.revolut.test.service.impl;

import com.revolut.test.dto.AccountRequest;
import com.revolut.test.dto.AccountResponse;
import com.revolut.test.model.Account;
import com.revolut.test.repository.AccountRepository;
import com.revolut.test.repository.impl.AccountRepositoryImpl;
import com.revolut.test.service.TransferService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TransferServiceImplTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private TransferService service = new TransferServiceImpl();

    @Test
    public void transferMoneyTest() {
        Account accountFrom = new Account(123, BigDecimal.valueOf(300));
        Account accountTo = new Account(321, BigDecimal.valueOf(100));

        AccountRequest request = new AccountRequest();
        request.setAccountNumberFrom(123);
        request.setRecipientAccount(321);
        request.setTransferAmount(BigDecimal.valueOf(100));
        request.setPaymentPurpose("Happy birthday");

        when(repository.findAccountByNumberWithLock(123)).thenReturn(accountFrom);
        when(repository.findAccountByNumberWithLock(321)).thenReturn(accountTo);
        AccountResponse response = service.transferMoney(request);

        Assert.assertEquals(response.getTransferAmount(), request.getTransferAmount());
        Assert.assertEquals(response.getAccountNumberFrom(), request.getAccountNumberFrom());
        Assert.assertEquals(response.getPaymentPurpose(), request.getPaymentPurpose());
        Assert.assertEquals(response.getRecipientAccount(), request.getRecipientAccount());
        Assert.assertEquals(response.getAccountAmountFrom(), BigDecimal.valueOf(200));
        Assert.assertEquals(response.getAccountAmountTo(), BigDecimal.valueOf(200));

    }
}