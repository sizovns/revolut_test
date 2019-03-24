package com.revolut.test.model;

import java.math.BigDecimal;

/**
 * Account
 */
public class Account {

    /**
     * Number of account
     */
    private long id;

    /**
     * Amount on a account
     */
    private BigDecimal amount;


    public Account(long id, BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }

}
