package com.revolut.test.dto;

import java.math.BigDecimal;

// название класса не соответствует документации по классу
/**
 * Ответ по переводу средств
 */
public class AccountResponse {

    // почему нет информации о счете с которого производится перевод?
    /**
     * Номер счета на который был выполнен перевод
     */
    private long accountNumber;

    /**
     * Текущая сумма на счете
     */
    private BigDecimal accountAmount;

    /**
     * Сумма перевода
     */
    private BigDecimal transferAmount;

    /**
     * Цель платежа
     */
    private String paymentPurpose;

    /**
     * причина отказа
     */
    private String rejectionReason;

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(BigDecimal accountAmount) {
        this.accountAmount = accountAmount;
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

    @Override
    public String toString() {
        return "AccountResponse{" +
                "accountNumber=" + accountNumber +
                ", accountAmount=" + accountAmount +
                ", transferAmount=" + transferAmount +
                ", paymentPurpose='" + paymentPurpose + '\'' +
                ", rejectionReason='" + rejectionReason + '\'' +
                '}';
    }

}
