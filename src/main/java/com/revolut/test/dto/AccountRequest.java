package com.revolut.test.dto;

import java.math.BigDecimal;

/**
 * Запрос на перевода средств
 */
public class AccountRequest {

    /**
     * Цель платежа
     */
    private String paymentPurpose;

    /**
     * Номер счета с которого выполняем перевод
     */
    private long accountNumberFrom;

    /**
     * Номер счета на который выполняем перевод
     */
    private long accountNumberTo;

    /**
     * Сумма перевода
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

    public long getAccountNumberTo() {
        return accountNumberTo;
    }

    public void setAccountNumberTo(long accountNumberTo) {
        this.accountNumberTo = accountNumberTo;
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
                ", accountNumberTo=" + accountNumberTo +
                ", transferAmount=" + transferAmount +
                '}';
    }
}
