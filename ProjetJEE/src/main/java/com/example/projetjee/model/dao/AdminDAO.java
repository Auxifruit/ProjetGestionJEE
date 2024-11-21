package com.example.projetjee.model.dao;

import com.example.projetjee.util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminDAO {
    private static final String ADMIN_TABLE = "administrator";
    private static final String ADMIN_ID = "administratorId";

    public static void deleteAdminById(int adminID) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "DELETE FROM " + ADMIN_TABLE + " WHERE " + ADMIN_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, adminID);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addAdminInTable(int adminID) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "INSERT INTO " + ADMIN_TABLE + " VALUES(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, adminID);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}