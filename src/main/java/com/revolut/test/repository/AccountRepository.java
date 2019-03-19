package com.revolut.test.repository;

import com.revolut.test.model.Account;
import javassist.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface AccountRepository {
    Account findAccountByNumberWithLock(long account);
    void saveAccount(Account account) throws SQLException;
    void updateAccount(Account account);
    List<Account> getAllAccounts();
}
