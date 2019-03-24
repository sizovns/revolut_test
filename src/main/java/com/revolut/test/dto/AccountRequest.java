package com.revolut.test.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

/**
 * Request for transfer money
 */
@XmlRootElement
public class AccountRequest {

    /**
     * Purpose of payment
     */
    private String paymentPurpose;

    /**
     * Sender's account
     */
    private long accountNumberFrom;

    /**
     * Recipient account
     */
    private long recipientAccount;

    /**
     * Transfer amount
     */
    private BigDecimal transferAmount;


    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    public void setPaymentPurpose(String paymentPurpose) {
        this.paymentPurpose = paymentPurpose;
    }

    public long getAccountNumberFrom() {
        return accountNumberFrom;
    }

    public void setAccountNumberFrom(long accountNumberFrom) {
        this.accountNumberFrom = accountNumberFrom;
    }

    public long getRecipientAccount() {
        return recipientAccount;
    }

    public void setRecipientAccount(long recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    @Override
    public String toString() {
        return "AccountRequest{" +
                "paymentPurpose='" + paymentPurpose + '\'' +
                ", accountNumberFrom=" + accountNumberFrom +
                ", recipientAccount=" + recipientAccount +
                ", transferAmount=" + transferAmount +
                '}';
    }
}
