package comp.trainticketserver.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/traintiketdb";
    private static final String USER = "root";
    private static final String PASSWORD = "123456a@";

    public static Connection getConnection() throws SQLException {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver không tìm thấy.", e);
        }
    }
}
