package com.revolut.test.dto;

import java.math.BigDecimal;

/**
 * Ответ по переводу средств
 * Response for transfer money
 */
public class AccountResponse {

    /**
     * Account number to transfer money
     * Номер счета на который был выполнен перевод
     */
    private long accountNumberTo;

    /**
     * Account number from transfer was made
     * Номер счета с которого был выполнен перевод
     */
    private long accountNumberFrom;

    /**
     * Amount of account to transfer money
     * Количество средств на аккаунте с короторого был выполнен перевод (остаток)
     */
    private BigDecimal accountAmountTo;

    /**
     * Amount of account to transfer money
     * Количество средств на аккаунте куда был выполнен перевод
     */
    private BigDecimal accountAmountFrom;

    /**
     * Transfer money amount
     * Сумма перевода
     */
    private BigDecimal transferAmount;

    /**
     * Purpose of payment
     * Цель платежа
     */
    private String paymentPurpose;

    /**
     * Reason of rejection
     * причина отказа
     */
    private String rejectionReason;

    public long getAccountNumberTo() {
        return accountNumberTo;
    }

    public void setAccountNumberTo(long accountNumberTo) {
        this.accountNumberTo = accountNumberTo;
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
                "accountNumberTo=" + accountNumberTo +
                ", accountNumberFrom=" + accountNumberFrom +
                ", accountAmountTo=" + accountAmountTo +
                ", accountAmountFrom=" + accountAmountFrom +
                ", transferAmount=" + transferAmount +
                ", paymentPurpose='" + paymentPurpose + '\'' +
                ", rejectionReason='" + rejectionReason + '\'' +
                '}';
    }

}
