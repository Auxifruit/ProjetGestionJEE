package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Utilisateur;
import com.example.projetjee.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {
    private static final String USER_TABLE = "utilisateur";
    private static final String USER_ID = "idUtilisateur";
    private static final String ID_ROLE = "idRole";

    /**
     * Method to get a list of all the users
     * @param roleFilter the role we want the list to be full of
     * @return a list of all the users according to the role filter
     */
    public static List<Utilisateur> getAllUsers(String roleFilter) {
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

    /**
     * Method to get the role id of an user by its id
     * @param userId the user's id
     * @return the user's role id
     */
    public static int getRoleIdByUserID(int userId) {
        int roleId = -1;

        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT " + ID_ROLE + " FROM " + USER_TABLE + " WHERE " + USER_ID + " = " + userId;

            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                roleId = resultSet.getInt("idRole");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roleId;
    }

    public static boolean modifyUserRole(int userID, int oldRoleID, int newRoleID) {
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            String query = "UPDATE " + USER_TABLE + " SET " + ID_ROLE + " = " + newRoleID + " WHERE " + USER_ID + " = " + userID;

            statement.executeUpdate(query);

            switch (oldRoleID) {
                case 1:
                    StudentDAO.deleteStudentById(userID);
                    break;
                case 2:
                    TeacherDAO.deleteTeacherById(userID);
                    break;
                case 3:
                    AdminDAO.deleteAdminById(userID);
                    break;
            }

            switch (newRoleID) {
                case 1:
                    StudentDAO.addStudentById(userID);
                    break;
                case 2:
                    TeacherDAO.addTeacherInTable(userID);
                    break;
                case 3:
                    AdminDAO.addAdminInTable(userID);
                    break;
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}
