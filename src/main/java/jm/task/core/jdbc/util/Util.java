package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/database";
    private static final String DB_USERNAME = "root1";
    private static final String DB_PASSWORD = "root1";

    public static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            connection.setAutoCommit(false);
            System.out.println("Connection OK");
        } catch (ClassNotFoundException  | SQLException e) {
            System.out.println("Error connection");
        }
        return connection;
    }
}
