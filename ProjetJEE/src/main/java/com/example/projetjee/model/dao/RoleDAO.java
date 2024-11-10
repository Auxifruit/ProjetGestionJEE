package com.example.projetjee.model.dao;

import com.example.projetjee.util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAO {
    public static String getRoleNameById(int roleId) {
        String roleName = " ";
        String query = "SELECT nomRole FROM RolePossible WHERE idRole = ?";

        try {
            Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, roleId);  // Set the idRole as parameter
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                roleName = resultSet.getString("nomRole");  // Get the role name
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roleName;
    }
}
