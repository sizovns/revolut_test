package com.revolut.test.repository.impl;

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

import static com.revolut.test.util.ConnectionPerThreadManager.createConnection;
import static com.revolut.test.util.ConnectionPerThreadManager.getConnection;

public class AccountRepositoryImpl implements AccountRepository {

    private static final Logger log = LoggerFactory.getLogger(AccountRepositoryImpl.class);

    public AccountRepositoryImpl() {
        createConnection();
    }


    @Override
    public Account findAccountByNumberWithLock(long accountId) {
        Connection connection;
        Account account = null;
        try {
            connection = getConnection();
            log.info("Operation \"Find account by id\" begin, id: {}", accountId);
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from ACCOUNT where id=" + accountId + " FOR UPDATE");
            while (rs.next()) {
                account = new Account(rs.getLong("id"), rs.getBigDecimal("amount"));
            }
        } catch (SQLException e) {
            log.error("SQLException an operation \"Find account by id\", exception: "
                    + Arrays.toString(e.getStackTrace()));
        }
        if (account == null) {
            log.error("Cannot find account by id: {}", accountId);
            throw new NotFoundAccountException("Account number " + accountId + " not found");

        }
        log.info("Operation \"Find account by id\" was done success");
        return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        log.info("Operation \"Get all accounts\" begin");
        List<Account> accountList = new ArrayList<>();
        Connection connection;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from ACCOUNT");
            while (rs.next()) {
                Account account = new Account(rs.getLong("id"), rs.getBigDecimal("amount"));
                accountList.add(account);
            }
        } catch (SQLException e) {
            log.error("SQLException an operation \"Get all accounts\", exception: "
                    + Arrays.toString(e.getStackTrace()));

        }
        log.info("Operation \"Get all accounts\" was done success");
        return accountList;
    }


    @Override
    public void updateAccount(Account account) {
        log.info("Operation \"Update Account\" begin");
        accountVerification(account);
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();
            stmt.execute("update ACCOUNT set amount=" + account.getAmount() + " where id=" + account.getId());
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("SQLException an operation \"Update Account\", exception: "
                        + Arrays.toString(e1.getStackTrace()));
            }
            log.error("SQLException an operation \"Update Account\", exception: "
                    + Arrays.toString(e.getStackTrace()));
        }
        log.info("Operation \"Update Account\" was done success");
    }

    private void accountVerification(Account account) {
        if (account == null || account.getId() == 0) {
            log.error("Bad data Account, account is null or id is 0");
            throw new BadDataException("Bad account data, ERROR SAVE");
        }
    }

}
