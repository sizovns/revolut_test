package com.revolut.test.util;

import com.revolut.test.configuration.impl.BasicConnectionPool;
import com.revolut.test.configuration.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public class ConnectionPerThreadManager {

    private static final Logger log = LoggerFactory.getLogger(ConnectionPerThreadManager.class);

    private static ConnectionPool connectionPool;

    private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    public static Connection getConnection() {
        return connectionThreadLocal.get();
    }

    public static void createConnection() {
        Connection connection;
        try {
            connectionPool = BasicConnectionPool
                    .create();
            connection = connectionPool.getConnection();
            connectionThreadLocal.set(connection);
        } catch (SQLException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

    public static void closeConnection() {
        Connection connection = connectionThreadLocal.get();
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.releaseConnection(connection);
        connectionThreadLocal.remove();
    }

}
