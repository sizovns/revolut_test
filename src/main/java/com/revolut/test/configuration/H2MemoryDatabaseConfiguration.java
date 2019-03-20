package com.revolut.test.configuration;

import java.io.PrintStream;
import java.sql.*;

public class H2MemoryDatabaseConfiguration {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";


    public Connection getDBConnection() {
        Connection dbConnection = null;
        // вызывается каждый раз когда создается соединение
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
//            logCurrentSessionsCount(dbConnection, System.out);
            return dbConnection;
        } catch (SQLException e) {
            // почему не логгер?
            System.out.println(e.getMessage());
        }
        // зачем? idea говорит: Value "dbConnection" is always 'null'...
        // нужно выбрасывать исключение
        return dbConnection;
    }

    public void createTableAndInsertData() {
        // представим что не удалось создать соединение и мы продолжим работать как будто ничего не случилось?
        // выбросит NPE и приехали
        Connection connection = getDBConnection();
        Statement stmt = null;
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            stmt.execute("CREATE TABLE ACCOUNT(id LONG primary key, amount INT8)");
            stmt.execute("INSERT INTO ACCOUNT(id, amount) VALUES(123, 1200)");
            stmt.execute("INSERT INTO ACCOUNT(id, amount) VALUES(321, 3500)");
            stmt.execute("INSERT INTO ACCOUNT(id, amount) VALUES(213, 23000)");
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            // почему не логгер?
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            // почему не логгер?
            e.printStackTrace();
            // представим что не удалось создать таблицу и мы продолжим работать как будто ничего не случилось?
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                // почему не логгер?
                e.printStackTrace();
            }
        }
    }

    /**
     * Выводит текущее количество сессий с БД
     *
     * @param conn cоединение с БД
     * @param out  стрим для записи информации, включая ошибки
     */
    private void logCurrentSessionsCount(Connection conn, PrintStream out) {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select count(*) from information_schema.sessions");
            while (rs.next()) {
                out.println("Current connections count: " + rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }

}
