package com.example.projetjee.model.dao;

import com.example.projetjee.util.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminDAO {
    private static final String ADMIN_TABLE = "administrateur";
    private static final String ADMIN_ID = "idAdministrateur";

    public static void deleteAdminById(int adminID) {
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM " + ADMIN_TABLE + " WHERE " + ADMIN_ID + " = " + adminID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addAdminInTable(int adminID) {
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO " + ADMIN_TABLE + " VALUES(" + adminID + ")");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
