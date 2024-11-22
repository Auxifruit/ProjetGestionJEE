package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Classes;
import com.example.projetjee.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClasseDAO {
    private static final String CLASS_TABLE = "Classes";
    private static final String CLASS_ID = "classId";
    private static final String CLASS_NAME = "className";

    public static List<Classes> getAllClasses() {
        List<Classes> allClasses = new ArrayList<>();
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT * FROM " + CLASS_TABLE;
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                Classes classe = new Classes();

                classe.setClassId(resultSet.getInt(CLASS_ID));
                classe.setClassName(resultSet.getString(CLASS_NAME));

                allClasses.add(classe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return allClasses;
    }

    public static Classes getClasse(int classeId) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT * FROM " + CLASS_TABLE + " WHERE " + CLASS_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, classeId);

            ResultSet resultSet = preparedStatement.executeQuery();

            Classes classe = new Classes();

            if (resultSet.next()) {
                classe.setClassId(resultSet.getInt(CLASS_ID));
                classe.setClassName(resultSet.getString(CLASS_NAME));
            }
            return classe;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean addClassesInTable(String className) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "INSERT INTO " + CLASS_TABLE + " VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setString(2, className);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteClassesFromTable(int classId) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "DELETE FROM " + CLASS_TABLE + " WHERE " + CLASS_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, classId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean modifyClassesFromTable(int classesId, String classesNewName) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "UPDATE " + CLASS_TABLE + " SET " + CLASS_NAME + " = ? WHERE " + CLASS_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, classesNewName);
            preparedStatement.setInt(2, classesId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getClassesNameById(int classId) {
        if(classId <= 0) {
            return null;
        }

        String className = null;

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT " + CLASS_NAME +" FROM " + CLASS_TABLE + " WHERE " + CLASS_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, classId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                className = resultSet.getString(CLASS_NAME);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return className;
    }

    public static boolean isClassesInTable(String className) {
        if(className == null || className.isEmpty()) {
            return false;
        }
        boolean isIn = true;

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT COUNT(*) FROM " + CLASS_TABLE + " WHERE " + CLASS_NAME + " LIKE(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, className);

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

    public static List<Classes> getAvailableClassesForLesson(int lessonId) {
        List<Classes> availableClasses = new ArrayList<>();

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT c." + CLASS_ID + ", c." + CLASS_NAME + " FROM " + CLASS_TABLE + " c WHERE c." + CLASS_ID + " NOT IN (SELECT sc." + CLASS_ID + " FROM LessonClass sc WHERE sc.lessonId = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, lessonId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Classes classe = new Classes();
                int classId = resultSet.getInt(CLASS_ID);
                String className = resultSet.getString(CLASS_NAME);

                classe.setClassId(classId);
                classe.setClassName(className);

                availableClasses.add(classe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableClasses;
    }
}
