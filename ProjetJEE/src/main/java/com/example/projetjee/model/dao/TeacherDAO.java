package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Teacher;
import com.example.projetjee.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    private static final String TEACHER_TABLE = "teacher";
    private static final String TEACHER_ID = "teacherID";

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

    public static List<String> getAllDiscipline(int teacherID) {
        List<String> discipline = new ArrayList<>();
        // the query to search the right discipline by joining in the database "lesson"
        String query = "SELECT DISTINCT Course.courseName " +
                "FROM Course " +
                "JOIN Lesson ON Course.courseId = Lesson.courseId " +
                "JOIN Teacher ON Lesson.teacherId = Teacher.teacherId " +
                "WHERE Teacher.teacherId = ?"; // TeacherID is the parameter of the prepared statement
        try {
            Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, teacherID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                discipline.add(resultSet.getString("courseName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return discipline;
    }

    public static List<String> getAllClasses(int teacherID) {
        List<String> classesList = new ArrayList<>();
        String query = "SELECT DISTINCT Classes.classesName " +
                "FROM Lesson " +
                "JOIN LessonClass ON Lesson.lessonId = LessonClass.lessonId " +
                "JOIN Classes ON LessonClass.classesId = Classes.classesId " +
                "WHERE Lesson.teacherId = ?";  // TeacherID is the parameter of the prepared statement

        try {
            Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, teacherID);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String className = resultSet.getString("classesName");
                classesList.add(className);
            }

        } catch (SQLException e) {
            System.out.println("Erreur recuperation classes" + e.getMessage()); // logs
            e.printStackTrace();
        }

        return classesList;
    }
}
