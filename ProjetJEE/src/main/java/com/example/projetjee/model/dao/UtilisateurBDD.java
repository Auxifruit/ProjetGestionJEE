package com.example.projetjee;

import models.Etudiant;
import models.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurBDD {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/cydatabase";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    public List<Utilisateur> getAllUsers(String roleFilter) {
        List<Utilisateur> userList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM utilisateur";

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
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
}
