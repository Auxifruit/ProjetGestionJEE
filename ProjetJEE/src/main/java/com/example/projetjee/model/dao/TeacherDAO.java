package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Teacher;
import com.example.projetjee.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    private static final String TEACHER_TABLE = "teacher";
    private static final String TEACHER_ID = "teacherId";

    public static List<Teacher> getAllTeachers() {
        List<Teacher> teacherList = new ArrayList<>();
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM " + TEACHER_TABLE;

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setTeacherId(resultSet.getInt(TEACHER_ID));

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
