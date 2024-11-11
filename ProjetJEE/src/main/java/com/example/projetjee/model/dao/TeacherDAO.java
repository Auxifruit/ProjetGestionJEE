package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Enseignant;
import com.example.projetjee.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    private static final String TEACHER_TABLE = "enseignant";
    private static final String TEACHER_ID = "idEnseignant";

    public static List<Enseignant> getAllTeachers() {
        List<Enseignant> teacherList = new ArrayList<>();
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM " + TEACHER_TABLE;

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Enseignant teacher = new Enseignant();
                teacher.setIdEnseignant(resultSet.getInt(TEACHER_ID));

                teacherList.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teacherList;
    }

    public static void deleteTeacherById(int teacherID) {
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM " + TEACHER_TABLE + " WHERE " + TEACHER_ID + " = " + teacherID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addTeacherInTable(int teacherID) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "INSERT INTO " + TEACHER_TABLE + " VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, teacherID);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
