package com.revolut.test.repository;

import com.revolut.test.model.Account;
import javassist.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface AccountRepository {
    /**
     * Find account by number and lock row
     *
     * @param account  account number
     * @return account
     */
    Account findAccountByNumberWithLock(long account);

    /**
     * Save account
     *
     * @param account {@link Account}
     */
    void saveAccount(Account account);

    /**
     * Update data in account
     *
     * @param account {@link Account}
     */
    void updateAccount(Account account);

    /**
     * Get all accounts in DB
     *
     * @return List of accounts {@link Account}
     */
    List<Account> getAllAccounts();

}
