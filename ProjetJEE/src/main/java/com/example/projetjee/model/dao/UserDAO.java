package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Users;
import com.example.projetjee.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Method to get the user's class by his id
     * @return the user's class
     */
    public static List<Map<String, Object>> getAllUsersWithClasses() {
        List<Map<String, Object>> usersWithClasses = new ArrayList<>();

        String query = "SELECT u.userId, u.userName, u.userLastName, u.userEmail, u.userBirthdate, c.classId, c.className " +
                "FROM users u " +
                "JOIN student s ON u.userId = s.studentId " +
                "JOIN classes c ON s.classId = c.classId";

        try {
            Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Map<String, Object> userWithClass = new HashMap<>();
                userWithClass.put("userId", resultSet.getInt("userId"));
                userWithClass.put("userName", resultSet.getString("userName"));
                userWithClass.put("userLastName", resultSet.getString("userLastName"));
                userWithClass.put("userEmail", resultSet.getString("userEmail"));

                // Conversion de la date
                userWithClass.put("userBirthdate", resultSet.getString("userBirthdate"));

                userWithClass.put("classId", resultSet.getInt("classId"));
                userWithClass.put("className", resultSet.getString("className"));
                usersWithClasses.add(userWithClass);
            }
        } catch (SQLException e) {
            System.out.println("Error in getAllUsersWithClasses:");
            e.printStackTrace();
        }

        return usersWithClasses;
    }

    /**
     * Method to get all distinct class names from the Classes table.
     * @return a list of class names.
     */
    public static List<String> getAllClassNames() {
        List<String> classNames = new ArrayList<>();

        // Requête SQL pour récupérer les noms de classes distincts
        String query = "SELECT DISTINCT className FROM Classes";  // Assure-toi que 'className' est la bonne colonne dans la table 'Classes'

        try {
            Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Ajouter le nom de classe à la liste
                classNames.add(resultSet.getString("className"));
            }

        } catch (SQLException e) {
            System.out.println("AllClass");
            e.printStackTrace();  // Gérer l'exception si quelque chose ne va pas avec la requête
        }

        return classNames;
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