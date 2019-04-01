package com.revolut.test.repository.impl;

import com.revolut.test.exception.BadDataException;
import com.revolut.test.exception.NotFoundAccountException;
import com.revolut.test.model.Account;
import com.revolut.test.util.ConnectionPerThreadManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ConnectionPerThreadManager.class})
public class AccountRepositoryImplTest {

    @Before
    public void setup() {
        PowerMockito.mockStatic(ConnectionPerThreadManager.class);
    }

    @Test
    public void testFindAccountById() throws Exception {
        final Statement statement = mock(Statement.class);
        final Connection connection = mock(Connection.class);
        final ResultSet rs = mock(ResultSet.class);

        when(ConnectionPerThreadManager.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(any())).thenReturn(rs);

        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getLong("id")).thenReturn(1L);
        when(rs.getBigDecimal("amount")).thenReturn(BigDecimal.ONE);


        final AccountRepositoryImpl repository = new AccountRepositoryImpl();
        Account account = repository.findAccountByNumberWithLock(1L);

        assertNotNull(account);
        assertEquals(1L, account.getId());
        assertEquals(BigDecimal.ONE, account.getAmount());

    }

    @Test(expected = NotFoundAccountException.class)
    public void testFindAccountById_throwsException() throws Exception {
        final Statement statement = mock(Statement.class);
        final Connection connection = mock(Connection.class);

        when(ConnectionPerThreadManager.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(any())).thenThrow(SQLException.class);

        final AccountRepositoryImpl repository = new AccountRepositoryImpl();
        Account account = repository.findAccountByNumberWithLock(1L);

        assertNull(account);

    }


    @Test
    public void testUpdateAccount() throws Exception {
        final Statement statement = mock(Statement.class);
        final Connection connection = mock(Connection.class);

        when(ConnectionPerThreadManager.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.execute(any())).thenReturn(true);

        final AccountRepositoryImpl repository = new AccountRepositoryImpl();
        Account account = new Account(1L, BigDecimal.TEN);
        repository.updateAccount(account);

    }


    @Test
    public void testUpdateAccount_throwsException() throws Exception {
        final Statement statement = mock(Statement.class);
        final Connection connection = mock(Connection.class);

        when(ConnectionPerThreadManager.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.execute(any())).thenThrow(SQLException.class);

        final AccountRepositoryImpl repository = new AccountRepositoryImpl();
        Account account = new Account(1L, BigDecimal.TEN);
        repository.updateAccount(account);

    }

    @Test(expected = BadDataException.class)
    public void testUpdateAccount_accountNull() throws Exception {
        final Statement statement = mock(Statement.class);
        final Connection connection = mock(Connection.class);

        when(ConnectionPerThreadManager.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.execute(any())).thenReturn(true);

        final AccountRepositoryImpl repository = new AccountRepositoryImpl();
        repository.updateAccount(null);

    }


}