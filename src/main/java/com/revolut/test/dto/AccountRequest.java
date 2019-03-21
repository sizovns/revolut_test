package com.revolut.test.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

/**
 * Request for transfer money
 * Запрос на перевода средств
 */
@XmlRootElement
public class AccountRequest {

    /**
     * Purpose of payment
     * Цель платежа
     */
    private String paymentPurpose;

    /**
     * Amount of account to transfer money
     * Номер счета с которого выполняем перевод
     */
    private long accountNumberFrom;

    /**
     * Amount of account to transfer money
     * Номер счета на который выполняем перевод
     */
    private long accountNumberTo;

    /**
     * Transfer money amount
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
