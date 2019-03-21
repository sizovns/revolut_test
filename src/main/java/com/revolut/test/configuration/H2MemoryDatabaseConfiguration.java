package com.revolut.test.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Deprecated
public class H2MemoryDatabaseConfiguration {

    private static final Logger log = LoggerFactory.getLogger(H2MemoryDatabaseConfiguration.class);


    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";


    public Connection getDBConnection() {
        log.info("Try to connect to DB");
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());
        }
        try {
            Connection dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            log.error("SQLException an getDBConnection {}", e.getMessage());
        }
        return null;
    }

    public void createTableAndInsertData() {
        log.info("Try to create table and insert data to DB");
        Connection connection = getDBConnection();
        try {
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE ACCOUNT(id LONG primary key, amount INT8)");
            stmt.execute("INSERT INTO ACCOUNT(id, amount) VALUES(123, 1200)");
            stmt.execute("INSERT INTO ACCOUNT(id, amount) VALUES(321, 3500)");
            stmt.execute("INSERT INTO ACCOUNT(id, amount) VALUES(213, 23000)");
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            log.error("SQLException an createTableAndInsertData {}", e.getMessage());
        } catch (Exception e) {
            log.error("Exception an createTableAndInsertData {}", e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("SQLException an createTableAndInsertData " +
                        "when connection close {}", e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
