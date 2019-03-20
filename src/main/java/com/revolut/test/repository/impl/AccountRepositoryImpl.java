package com.revolut.test.repository.impl;

import com.revolut.test.configuration.H2MemoryDatabaseConfiguration;
import com.revolut.test.exception.BadDataException;
import com.revolut.test.exception.NotFoundAccountException;
import com.revolut.test.model.Account;
import com.revolut.test.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {

    private static final Logger log = LoggerFactory.getLogger(AccountRepositoryImpl.class);


    // 1) множества разных конфигураций нет, зачем еще один инстанс к уже существующему в RestServer?
    // b) а почему тут нет интерфейса? какие мотивы?
    private static final H2MemoryDatabaseConfiguration configuration = new H2MemoryDatabaseConfiguration();

    // откуда транзакции из JavaEE?
    @Transactional
    @Override
    // find by NUMBER или accountId? путаешь читателя
    public Account findAccountByNumberWithLock(long accountId) {
        // почему info, а не trace/debug?
        log.info("Operation \"Find account by id\" begin, id: {}", accountId);
        // следи за руками
        // 1) создание НОВОГО соединение с БД... каждый раз НОВОЕ соединение с БД
        Connection connection = configuration.getDBConnection();
        Account account = null;
        try {
            // 2) делаем запрос через соединение
            Statement stmt = connection.createStatement();
            // выполняем блокировку, получаем результат и тут же отпускаем блокировку
            // не о такой одномоментной блокировке мы говорили
            ResultSet rs = stmt.executeQuery("select * from ACCOUNT where id=" + accountId + "FOR UPDATE");
            while (rs.next()) {
                account = new Account(rs.getLong("id"), rs.getBigDecimal("amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // стектрейс в лог не попало
            log.error("SQLException an operation \"Find account by id\", exception: " + e.getMessage());
        }
        if (account == null) {
            log.error("Cannot find account by id: {}", accountId);
            throw new NotFoundAccountException("Account number " + accountId + " not found");

        }
        // почему info, а не trace/debug?
        log.info("Operation \"Find account by id\" was done success");
        // 3) а куда делось соединение с БД? утекло
        // 4) конец
        return account;
    }

    // метод не используется
    @Override
    public List<Account> getAllAccounts() {
        log.info("Operation \"Get all accounts\" begin");
        List<Account> accountList = new ArrayList<>();
        // история про утекающие соединения в методе findAccountByNumberWithLock
        Connection connection = configuration.getDBConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from ACCOUNT");
            while (rs.next()) {
                Account account = new Account(rs.getLong("id"), rs.getBigDecimal("amount"));
                accountList.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQLException an operation \"Get all accounts\", exception: " + e.getMessage());

        }
        log.info("Operation \"Get all accounts\" was done success");
        return accountList;
    }

    // метод не используется
    @Transactional
    @Override
    public void saveAccount(Account account) {
        log.info("Operation \"Save Account\" begin");
        accountVerification(account);
        Connection connection = configuration.getDBConnection();
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("INSERT INTO ACCOUNT(id, amount) VALUES(" + account.getId() + ", " + account.getAmount() + ")");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQLException an operation \"Save Account\", exception: " + e.getMessage());
        }
        log.info("Operation \"Save Account\" was done success");

    }

    @Transactional
    @Override
    public void updateAccount(Account account) {
        log.info("Operation \"Update Account\" begin");
        accountVerification(account);
        // создаем новое соединение с БД
        Connection connection = configuration.getDBConnection();
        try {
            Statement stmt = connection.createStatement();
            // а если account c указанным id не существует? ситуация не обработана
            stmt.execute("update ACCOUNT set amount=" + account.getAmount() + " where id=" + account.getId());
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            // стектрейс не попало в лог
            log.error("SQLException an operation \"Update Account\", exception: " + e.getMessage());
        }
        log.info("Operation \"Update Account\" was done success");

    }

    private void accountVerification(Account account) {
        if (account == null || account.getId() == 0) {
            log.error("Bad data Account, account is null or id is 0");
            // 'ERROR SAVE' - название метода не указывает на какую-либо принадлежность к SAVE
            throw new BadDataException("Bad account data, ERROR SAVE");
        }
    }

}
