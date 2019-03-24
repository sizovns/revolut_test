package com.revolut.test.dto;

import java.math.BigDecimal;

/**
 * Response for transfer money
 */
public class AccountResponse {

    /**
     * Recipient account
     */
    private long recipientAccount;

    /**
     * Sender's account
     */
    private long accountNumberFrom;

    /**
     * The amount of recipient account
     */
    private BigDecimal accountAmountTo;

    /**
     * Sender's account balance
     */
    private BigDecimal accountAmountFrom;

    /**
     * Transfer amount
     */
    private BigDecimal transferAmount;

    /**
     * Purpose of payment
     */
    private String paymentPurpose;

    /**
     * Reason of rejection
     */
    private String rejectionReason;

    public long getRecipientAccount() {
        return recipientAccount;
    }

    public void setRecipientAccount(long recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    public BigDecimal getAccountAmountTo() {
        return accountAmountTo;
    }

    public void setAccountAmountTo(BigDecimal accountAmountTo) {
        this.accountAmountTo = accountAmountTo;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    public void setPaymentPurpose(String paymentPurpose) {
        this.paymentPurpose = paymentPurpose;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public long getAccountNumberFrom() {
        return accountNumberFrom;
    }

    public void setAccountNumberFrom(long accountNumberFrom) {
        this.accountNumberFrom = accountNumberFrom;
    }

    public BigDecimal getAccountAmountFrom() {
        return accountAmountFrom;
    }

    public void setAccountAmountFrom(BigDecimal accountAmountFrom) {
        this.accountAmountFrom = accountAmountFrom;
    }

    @Override
    public String toString() {
        return "AccountResponse{" +
                "recipientAccount=" + recipientAccount +
                ", accountNumberFrom=" + accountNumberFrom +
                ", accountAmountTo=" + accountAmountTo +
                ", accountAmountFrom=" + accountAmountFrom +
                ", transferAmount=" + transferAmount +
                ", paymentPurpose='" + paymentPurpose + '\'' +
                ", rejectionReason='" + rejectionReason + '\'' +
                '}';
    }

}
