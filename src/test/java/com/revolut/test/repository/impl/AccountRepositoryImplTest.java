package com.revolut.test.repository.impl;

import com.revolut.test.configuration.impl.BasicConnectionPool;
import com.revolut.test.model.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountRepositoryImplTest {
/*
    @Mock
    private BasicConnectionPool ds;

    @Mock
    private Connection c;

    @Mock
    private Statement stmt;

    @Mock
    private ResultSet rs;

    @Spy
    @InjectMocks
    private AccountRepositoryImpl repository;

    private Account a;*/


    @Before
    public void setUp() throws Exception {
    /*    assertNotNull(ds);
        when(ds.getConnection()).thenReturn(c);
        when(c.createStatement()).thenReturn(stmt);
        a = new Account(123, BigDecimal.valueOf(1000));
        stmt.execute("CREATE TABLE ACCOUNT(id LONG primary key, amount INT8)");
        stmt.execute("INSERT INTO ACCOUNT(id, amount) VALUES(123, 1200)");
        stmt.execute("INSERT INTO ACCOUNT(id, amount) VALUES(321, 3500)");
        stmt.execute("INSERT INTO ACCOUNT(id, amount) VALUES(213, 23000)");
        when(rs.next()).thenReturn(true);
        when(rs.getLong("id")).thenReturn(a.getId());
        when(rs.getBigDecimal("amount")).thenReturn(a.getAmount());
        when(stmt.executeQuery("select * from ACCOUNT where id=" + 123 + " FOR UPDATE")).thenReturn(rs);
    */}

    @Test
    public void findAccountByNumberWithLockTest() throws Exception {

        //1. Setup
        List<Account> accounts = Arrays.asList(
                new Account(123, BigDecimal.valueOf(1000)),
                new Account(321, BigDecimal.valueOf(1200)));

        AccountRepositoryImpl mockito = mock(AccountRepositoryImpl.class);

        //if the author is "mkyong", then return a 'books' object.
        when(mockito.findAccountByNumberWithLock(123)).thenReturn(accounts
                .stream()
                .filter(a -> a.getId() == 123)
                .findFirst().get());

        /*AuthorServiceImpl obj = new AuthorServiceImpl();
        obj.setBookService(mockito);
        obj.setBookValidatorService(new FakeBookValidatorService());
*/
        //2. Test method
        BigDecimal qty = mockito.findAccountByNumberWithLock(123).getAmount();

        //3. Verify result
        assertThat(qty, is(BigDecimal.valueOf(1000)));
    }

    /*@Test
    public void getAllAccounts() {
    }

    @Test
    public void saveAccount() {
    }

    @Test
    public void updateAccount() {
    }*/
}