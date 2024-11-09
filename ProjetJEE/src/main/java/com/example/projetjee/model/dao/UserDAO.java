package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Utilisateur;
import com.example.projetjee.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/cydatabase";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    private static final String USER_TABLE = "utilisateur";

    public List<Utilisateur> getAllUsers(String roleFilter) {
        List<Utilisateur> userList = new ArrayList<>();
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM " + USER_TABLE;

            if (roleFilter != null && roleFilter != "") {
                query += " WHERE roleUtilisateur = '" + roleFilter + "'";
            }

            System.out.println(query);

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Utilisateur user = new Utilisateur();
                user.setIdUtilisateur(resultSet.getInt("idUtilisateur"));
                user.setIdentifiantUtilisateur(resultSet.getString("identifiantUtilisateur"));
                user.setRoleUtilisateur(resultSet.getString("roleUtilisateur"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }
}
