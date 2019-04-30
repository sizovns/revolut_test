package com.revolut.test.repository.impl;

import com.revolut.test.exception.BadDataException;
import com.revolut.test.exception.NotFoundAccountException;
import com.revolut.test.exception.TransactionException;
import com.revolut.test.model.Account;
import com.revolut.test.repository.AccountRepository;
import com.revolut.test.util.ConnectionPerThreadManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class AccountRepositoryImpl implements AccountRepository {

    private static final Logger log = LoggerFactory.getLogger(AccountRepositoryImpl.class);

    public AccountRepositoryImpl() {
        ConnectionPerThreadManager.createConnection();
    }


    @Override
    public Account findAccountByNumberWithLock(long accountId) {
        Connection connection;
        Account account = null;
        try {
            connection = ConnectionPerThreadManager.getConnection();
            log.debug("Operation \"Find account by id\" begin, id: {}", accountId);
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from ACCOUNT where id=" + accountId + " FOR UPDATE");
            while (rs.next()) {
                account = new Account(rs.getLong("id"), rs.getBigDecimal("amount"));
            }
            stmt.close();
        } catch (SQLException e) {
            log.error("SQLException an operation \"Find account by id\", exception: ", e);
        }
        if (account == null) {
            log.error("Cannot find account by id: {}", accountId);
            throw new NotFoundAccountException("Account number " + accountId + " not found");

        }
        log.debug("Operation \"Find account by id\" was done success");
        return account;
    }

    @Override
    public void updateAccount(Account account) {
        log.debug("Operation \"Update Account\" begin");
        accountVerification(account);
        Connection connection = null;
        try {
            connection = ConnectionPerThreadManager.getConnection();
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();
            stmt.execute("update ACCOUNT set amount=" + account.getAmount() + " where id=" + account.getId());
            stmt.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new TransactionException("Update account" + account.getId() + " have an error, transaction rollback");
            } catch (SQLException e1) {
                log.error("SQLException an operation \"Update Account\", exception: ", e);
            }
            log.error("SQLException an operation \"Update Account\", exception: ", e);
        }
        log.debug("Operation \"Update Account\" was done success");
    }

    private void accountVerification(Account account) {
        if (account == null || account.getId() == 0) {
            log.error("Bad data Account, account is null or id is 0");
            throw new BadDataException("Bad account data, ERROR SAVE");
        }
    }

}
