package com.revolut.test.util;

import com.revolut.test.configuration.impl.BasicConnectionPool;
import com.revolut.test.configuration.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class InitializeDb {

    private static ConnectionPool connectionPool;

    private static final Logger log = LoggerFactory.getLogger(InitializeDb.class);

    public InitializeDb() {
        try {
            connectionPool = BasicConnectionPool.create();
        } catch (SQLException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

    public void createTableAndInsertData() {
        log.info("Try to create table and insert data to DB");
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE ACCOUNT(id LONG primary key, amount INT8)");
            stmt.execute("INSERT INTO ACCOUNT(id, amount) VALUES(123, 1200)");
            stmt.execute("INSERT INTO ACCOUNT(id, amount) VALUES(321, 3500)");
            stmt.execute("INSERT INTO ACCOUNT(id, amount) VALUES(213, 23000)");
            stmt.close();
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            log.error("SQLException an createTableAndInsertData {}", e.getMessage());
        } catch (Exception e) {
            log.error("Exception an createTableAndInsertData {}", e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.error("SQLException an createTableAndInsertData " +
                        "when connection close {}", e.getStackTrace());
                e.printStackTrace();
            }
        }
        log.info("Ends create table and insert data to DB");

    }


}
