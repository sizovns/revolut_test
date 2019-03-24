package com.revolut.test.controller;

import com.revolut.test.dto.AccountRequest;
import com.revolut.test.dto.AccountResponse;
import com.revolut.test.exception.NotFoundAccountException;
import com.revolut.test.model.Account;
import com.revolut.test.service.TransferService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.Silent.class)
public class TransferControllerTest {

    @Mock
    private TransferService service;

    @InjectMocks
    private TransferController controller = new TransferController();

    @Test
    public void testController() {

        AccountRequest request = new AccountRequest();
        request.setAccountNumberFrom(123);
        request.setRecipientAccount(321);
        request.setTransferAmount(BigDecimal.valueOf(100));
        request.setPaymentPurpose("Happy birthday");

        AccountResponse response = new AccountResponse();
        response.setPaymentPurpose("Happy birthday");
        response.setAccountAmountFrom(BigDecimal.valueOf(200));
        response.setAccountNumberFrom(123);
        response.setRecipientAccount(321);
        response.setAccountAmountTo(BigDecimal.valueOf(200));
        response.setTransferAmount(BigDecimal.valueOf(100));

        when(service.transferMoney(request)).thenReturn(response);
        Response realResponse = controller.transferMoney(request);

        Assert.assertEquals(realResponse.getStatus(), 200);

        AccountResponse responseEntity = (AccountResponse) realResponse.getEntity();

        Assert.assertEquals(response.getTransferAmount(), responseEntity.getTransferAmount());
        Assert.assertEquals(response.getAccountNumberFrom(), responseEntity.getAccountNumberFrom());
        Assert.assertEquals(response.getPaymentPurpose(), responseEntity.getPaymentPurpose());
        Assert.assertEquals(response.getRecipientAccount(), responseEntity.getRecipientAccount());
        Assert.assertEquals(response.getAccountAmountFrom(), responseEntity.getAccountAmountFrom());


    }

    @Test
    public void negativeTestController() {
        AccountRequest request = new AccountRequest();
        request.setAccountNumberFrom(123);
        request.setRecipientAccount(321);
        request.setTransferAmount(BigDecimal.valueOf(100));
        request.setPaymentPurpose("Happy birthday");

        when(service.transferMoney(request)).thenThrow(new NotFoundAccountException());

        Response realResponse = controller.transferMoney(request);

        Assert.assertEquals(realResponse.getStatus(), 400);

        AccountResponse responseEntity = (AccountResponse) realResponse.getEntity();

        Assert.assertEquals(request.getTransferAmount(), responseEntity.getTransferAmount());
        Assert.assertEquals(request.getRecipientAccount(), responseEntity.getRecipientAccount());
        Assert.assertEquals("Not found account exception", responseEntity.getRejectionReason());
        Assert.assertEquals(request.getAccountNumberFrom(), responseEntity.getAccountNumberFrom());


    }

}