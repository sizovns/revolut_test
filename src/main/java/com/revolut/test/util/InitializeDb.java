package com.revolut.test.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class InitializeDb {

    private static final Logger log = LoggerFactory.getLogger(InitializeDb.class);

    public InitializeDb() {
        ConnectionPerThreadManager.createConnection();
    }

    public void createTableAndInsertData() {
        log.debug("Try to create table and insert data to DB");
        Connection connection = null;
        try {
            connection = ConnectionPerThreadManager.getConnection();
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE ACCOUNT(id LONG primary key, amount INT8)");
            stmt.execute("INSERT INTO ACCOUNT(id, amount) VALUES(123, 1200)");
            stmt.execute("INSERT INTO ACCOUNT(id, amount) VALUES(321, 3500)");
            stmt.execute("INSERT INTO ACCOUNT(id, amount) VALUES(213, 23000)");
            stmt.close();
            ConnectionPerThreadManager.closeConnection();
        } catch (SQLException e) {
            log.error("SQLException an createTableAndInsertData: ", e);
        } catch (Exception e) {
            log.error("Exception an createTableAndInsertData: ", e);
        }
        log.debug("Ends create table and insert data to DB");

    }


}
