package com.revolut.test.repository.impl;

import com.revolut.test.configuration.BasicConnectionPool;
import com.revolut.test.configuration.ConnectionPool;
import com.revolut.test.exception.BadDataException;
import com.revolut.test.exception.NotFoundAccountException;
import com.revolut.test.model.Account;
import com.revolut.test.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {

    private static ConnectionPool connectionPool;

    private static final Logger log = LoggerFactory.getLogger(AccountRepositoryImpl.class);

    public AccountRepositoryImpl() {
        try {
            connectionPool = BasicConnectionPool
                    .create();
        } catch (SQLException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }


    @Override
    public Account findAccountByNumberWithLock(long accountId) {
        Connection connection = null;
        Account account = null;
        try {
            log.info("Operation \"Find account by id\" begin, id: {}", accountId);
            connection = connectionPool.getConnection();

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from ACCOUNT where id=" + accountId + "FOR UPDATE");
            while (rs.next()) {
                account = new Account(rs.getLong("id"), rs.getBigDecimal("amount"));
            }
        } catch (SQLException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            log.error("SQLException an operation \"Find account by id\", exception: " + e.getMessage());
        }
        if (account == null) {
            log.error("Cannot find account by id: {}", accountId);
            throw new NotFoundAccountException("Account number " + accountId + " not found");

        }
        log.info("Operation \"Find account by id\" was done success");
        closeConnection("findAccountByNumberWithLock", connection);
        return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        log.info("Operation \"Get all accounts\" begin");
        List<Account> accountList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from ACCOUNT");
            while (rs.next()) {
                Account account = new Account(rs.getLong("id"), rs.getBigDecimal("amount"));
                accountList.add(account);
            }
        } catch (SQLException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            log.error("SQLException an operation \"Get all accounts\", exception: " + e.getMessage());

        }
        log.info("Operation \"Get all accounts\" was done success");
        closeConnection("getAllAccounts", connection);
        return accountList;
    }

    @Override
    public void saveAccount(Account account) {
        log.info("Operation \"Save Account\" begin");
        accountVerification(account);
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            Statement stmt = connection.createStatement();
            stmt.execute("INSERT INTO ACCOUNT(id, amount) VALUES("
                    + account.getId() + ", " + account.getAmount() + ")");
            connection.commit();
        } catch (SQLException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            log.error("SQLException an operation \"Save Account\", exception: " + e.getMessage());
        }
        log.info("Operation \"Save Account\" was done success");
        closeConnection("saveAccount", connection);

    }

    @Override
    public void updateAccount(Account account) {
        log.info("Operation \"Update Account\" begin");
        accountVerification(account);
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            Statement stmt = connection.createStatement();
            stmt.execute("update ACCOUNT set amount=" + account.getAmount() + " where id=" + account.getId());
            connection.commit();
        } catch (SQLException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            log.error("SQLException an operation \"Update Account\", exception: " + e.getMessage());
        }
        log.info("Operation \"Update Account\" was done success");
        closeConnection("updateAccount", connection);

    }

    private void accountVerification(Account account) {
        if (account == null || account.getId() == 0) {
            log.error("Bad data Account, account is null or id is 0");
            throw new BadDataException("Bad account data, ERROR SAVE");
        }
    }

    private void closeConnection(String methodName, Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            log.error("SQLException an {} " +
                    "when connection close {}", methodName, e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

}
