package com.revolut.test.controller;

import com.revolut.test.dto.AccountResponse;
import com.revolut.test.exception.BadDataException;
import com.revolut.test.exception.NoMoneyOnAccountException;
import com.revolut.test.exception.NotFoundAccountException;
import com.revolut.test.service.TransferService;
import com.revolut.test.service.impl.TransferServiceImpl;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;


@Path("transfer")
public class TransferController {

    // зачем тут интерфейс если тут же выбирается и создается реализация? зачем такие сложности?
    private TransferService service = new TransferServiceImpl();

    @POST
    @Path("money")
    @Produces(MediaType.APPLICATION_JSON)
    public Response transferMoney(@QueryParam("accountNumberFrom") long accountNumberFrom,
                                  @QueryParam("accountNumberTo") long accountNumberTo,
                                  @QueryParam("amount") BigDecimal amount,
                                  @QueryParam("paymentPurpose") String paymentPurpose) {
        AccountResponse accountResponse = new AccountResponse();
        try {
            accountResponse = service.transferMoney(accountNumberFrom, accountNumberTo, amount, paymentPurpose);
            // почему не логгер?
            System.out.println(accountResponse);
            return Response.status(200).entity(accountResponse).build();
        } catch (NotFoundAccountException | BadDataException | NoMoneyOnAccountException e) {
            // почему не логгер?
            // куда упадет стектрейс?
            e.printStackTrace();
            // почему accountNumberTo, а не accountNumberFrom?
            accountResponse.setAccountNumber(accountNumberTo);
            accountResponse.setRejectionReason(e.getMessage());
            return Response.status(401).entity(accountResponse).build();
        }

    }
}
