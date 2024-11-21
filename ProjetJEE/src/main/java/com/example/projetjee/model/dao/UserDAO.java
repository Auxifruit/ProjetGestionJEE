package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Users;
import com.example.projetjee.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String USER_TABLE = "users";
    private static final String USER_ID = "userId";
    private static final String USER_PASSWORD = "userPassword";
    private static final String USER_LASTNAME = "userLastName";
    private static final String USER_NAME = "userName";
    private static final String USER_EMAIL = "userEmail";
    private static final String USER_BIRTHDATE = "userBirthdate";
    private static final String ROLE_ID = "roleId";

    /**
     * Method to get a list of all the users
     * @param roleFilter the role we want the list to be full of
     * @return a list of all the users according to the role filter
     */
    public static List<Users> getAllUsers(String roleFilter) {
        List<Users> userList = new ArrayList<>();
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM " + USER_TABLE;

            if (roleFilter != null && roleFilter != "") {
                query += " WHERE " + ROLE_ID + " = " + roleFilter;
            }

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Users user = new Users();
                user.setUserId(resultSet.getInt(USER_ID));
                user.setUserLastName(resultSet.getString(USER_LASTNAME));
                user.setUserName(resultSet.getString(USER_NAME));
                user.setUserEmail(resultSet.getString(USER_EMAIL));
                user.setRoleId(resultSet.getInt(ROLE_ID));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public static boolean addUserInTable(String userPassword, String userLastName, String userName, String userEmail, String userBirthdate, int roleId) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "INSERT INTO " + USER_TABLE + "(" + USER_PASSWORD + ", " + USER_LASTNAME + ", " + USER_NAME + ", " + USER_EMAIL + ", " + USER_BIRTHDATE + ", " + ROLE_ID + ") VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, userPassword);
            preparedStatement.setString(2, userLastName);
            preparedStatement.setString(3, userName);
            preparedStatement.setString(4, userEmail);
            preparedStatement.setString(5, userBirthdate);
            preparedStatement.setInt(6, roleId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Une erreur est survenue lors de l'ajout d'un utilisateur dans la base de donnée");
            return false;
        }
        return true;
    }

    /**
     * Method to get the user's last name by his id
     * @param userId the user's id
     * @return the user's last name
     */
    public static String getLastNameById(int userId) {
        String lastName = null;

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT " + USER_LASTNAME + " FROM " + USER_TABLE + " WHERE " + USER_ID + " = " + userId;
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                lastName = resultSet.getString(USER_LASTNAME);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastName;
    }

    /**
     * Method to get the user's name by his id
     * @param userId the user's id
     * @return the user's name
     */
    public static String getNameById(int userId) {
        String name = null;

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT " + USER_NAME + " FROM " + USER_TABLE + " WHERE " + USER_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                name = resultSet.getString(USER_NAME);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return name;
    }

    /**
     * Method to get the user's role by his id
     * @param userId the user's id
     * @return the user's role id
     */
    public static int getRoleIdByUserID(int userId) {
        int roleId = -1;

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT " + ROLE_ID + " FROM " + USER_TABLE + " WHERE " + USER_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                roleId = resultSet.getInt(ROLE_ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roleId;
    }

    public static Integer getUserIdByEmail(String userMail) {
        if(userMail == null || userMail.isEmpty()) {
            return null;
        }

        Integer userId = null;

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT " + USER_ID + " FROM " + USER_TABLE + " WHERE " + USER_EMAIL + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, userMail);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userId = resultSet.getInt(USER_ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return userId;
    }

    public static boolean modifyUserRole(int userID, int oldRoleID, int newRoleID) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "UPDATE " + USER_TABLE + " SET " + ROLE_ID + " = ? WHERE " + USER_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, newRoleID);
            preparedStatement.setInt(2, userID);

            preparedStatement.executeUpdate();

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
                    StudentDAO.addStudentInTable(userID, null, null);
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

    public static boolean isEmailInTable(String email) {
        if(email == null || email.isEmpty()) {
            return false;
        }
        boolean isIn = true;

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT COUNT(*) FROM " + USER_TABLE + " WHERE " + USER_EMAIL + " LIKE(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                isIn = count != 0;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isIn;
    }

    public static Integer userConnection(String userEmail, String userPassword) {
        Integer userId = null;
        try {
            // Établir la connexion à la base de données
            Connection connection = DatabaseManager.getConnection();
            String sql = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_EMAIL + " = ? AND " + USER_PASSWORD + " = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, userEmail);
            statement.setString(2, userPassword);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                userId = resultSet.getInt(USER_ID);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return userId;
    }
}