package com.example.projetjee.model.dao;

import com.example.projetjee.util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAO {
    private static final String ROLE_TABLE = "possibleRole";
    private static final String ROLE_ID = "roleId";
    private static final String ROLE_NAME = "roleName";

    public static String getRoleNameById(int roleId) {
        String roleName = " ";

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT " + ROLE_NAME + " FROM " + ROLE_TABLE + " WHERE " + ROLE_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, roleId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                roleName = resultSet.getString(ROLE_NAME);  // Get the role name
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roleName;
    }
}