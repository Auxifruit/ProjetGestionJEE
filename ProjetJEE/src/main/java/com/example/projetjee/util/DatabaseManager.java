package com.example.projetjee.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/cydatabase";
    private static final String USER = "root";
    private static final String PASSWORD = "cytech0001";
    private static Connection connection;

    /**
     * Method to get the connection to the database
     * @return the connection to the database
     * @throws SQLException if we have sql error
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connection == null) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while connection to the data base.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
