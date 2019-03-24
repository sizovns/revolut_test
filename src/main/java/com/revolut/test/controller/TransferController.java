package com.revolut.test.controller;

import com.revolut.test.dto.AccountRequest;
import com.revolut.test.dto.AccountResponse;
import com.revolut.test.exception.BadDataException;
import com.revolut.test.exception.NoMoneyOnAccountException;
import com.revolut.test.exception.NotFoundAccountException;
import com.revolut.test.service.TransferService;
import com.revolut.test.service.impl.TransferServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;


@Path("transfer")
public class TransferController {

    private TransferService service = new TransferServiceImpl();

    private static final Logger log = LoggerFactory.getLogger(TransferController.class);

    @POST
    @Path("money")
    @Produces(MediaType.APPLICATION_JSON)
    public Response transferMoney(AccountRequest request) {
        log.info("Begin of Transfer Money with request: {}", request);
        AccountResponse accountResponse = new AccountResponse();
        try {
            accountResponse = service.transferMoney(request);
            log.info("Response: {}", accountResponse);
            return Response.status(200).entity(accountResponse).build();
        } catch (NotFoundAccountException | BadDataException | NoMoneyOnAccountException e) {
            log.error("transferMoney have an exception: {}", Arrays.toString(e.getStackTrace()));
            accountResponse.setRecipientAccount(request.getRecipientAccount());
            accountResponse.setAccountNumberFrom(request.getAccountNumberFrom());
            accountResponse.setTransferAmount(request.getTransferAmount());
            accountResponse.setRejectionReason(e.getMessage());
            log.info("Response: {}", accountResponse);
            return Response.status(400).entity(accountResponse).build();
        }

    }
}
