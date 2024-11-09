package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Utilisateur;
import com.example.projetjee.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String USER_TABLE = "utilisateur";

    public List<Utilisateur> getAllUsers(String roleFilter) {
        List<Utilisateur> userList = new ArrayList<>();
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM " + USER_TABLE;

            if (roleFilter != null && roleFilter != "") {
                query += " WHERE idRole = " + roleFilter;
            }

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Utilisateur user = new Utilisateur();
                user.setIdUtilisateur(resultSet.getInt("idUtilisateur"));
                user.setNomUtilisateur(resultSet.getString("nomUtilisateur"));
                user.setPrénomUtilisateur(resultSet.getString("prénomUtilisateur"));
                user.setEmailUtilisateur(resultSet.getString("emailUtilisateur"));
                user.setIdRole(resultSet.getInt("idRole"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }
}
