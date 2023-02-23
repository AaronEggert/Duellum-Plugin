package de.aaroneggert.duellum.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static String host = "jdbc:mysql://localhost/duellum";
    private static String user = "root";
    private static String pwd = "A-e:2703/06";

    public static Connection connection = null;

    public static void connect() throws SQLException {
        connection = DriverManager.getConnection(host, user, pwd);
    }

    public static void closeConnection () throws SQLException {
        connection.close();
    }

    public static Connection getConnection() {
        return connection;
    }


}
