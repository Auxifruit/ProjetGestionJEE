package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Classes;
import com.example.projetjee.model.entities.Lesson;
import com.example.projetjee.util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonClassesDAO {
    private static String LESSON_CLASS_TABLE = "LessonClass";
    private static String LESSON_CLASS_ID = "lessonClassId";
    private static String LESSON_ID = "lessonId";
    private static String CLASS_ID = "classId";
    public static List<Classes> getLessonClasses(int lessonId) {
        List<Classes> availableClasses = new ArrayList<>();

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT " + CLASS_ID + " FROM " + LESSON_CLASS_TABLE + " WHERE " + LESSON_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, lessonId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idClasse = resultSet.getInt(CLASS_ID);

                availableClasses.add(ClasseDAO.getClasse(idClasse));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableClasses;
    }

    public static boolean addLessonClassInTable(int lessonId, int classId) {
        if(lessonId <= 0 || classId <= 0) {
            return false;
        }

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "INSERT INTO " + LESSON_CLASS_TABLE + "(" + LESSON_ID + ", " + CLASS_ID + ") VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, lessonId);
            preparedStatement.setInt(2, classId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteLessonClassInTable(int lessonId, int classId) {
        if(lessonId <= 0 || classId <= 0) {
            return false;
        }

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "DELETE FROM " + LESSON_CLASS_TABLE + " WHERE " + LESSON_ID + " = ? AND " + CLASS_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, lessonId);
            preparedStatement.setInt(2, classId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean canClassParticipate(int classId, int lessonId) {
        Lesson lesson = LessonDAO.getLesson(lessonId);

        if (lesson == null) {
            return false;
        }

        String lessonStartDate = lesson.getLessonStartDate().toString();
        String lessonEndDate = lesson.getLessonEndDate().toString();

        String query = "SELECT COUNT(*) FROM " + LESSON_CLASS_TABLE +  " sc JOIN Lesson s ON sc." + LESSON_ID + " = s." + LESSON_ID +  " WHERE sc." + CLASS_ID + " = ? AND s." + LESSON_ID + " != ? AND ( (s.lessonStartDate < ? AND s.lessonEndDate > ?) OR (s.lessonStartDate < ? AND s.lessonEndDate > ?) OR (s.lessonStartDate >= ? AND s.lessonEndDate <= ?)); ";

        try {
            Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, classId);
            preparedStatement.setInt(2, lessonId);
            preparedStatement.setString(3, lessonEndDate);
            preparedStatement.setString(4, lessonStartDate);
            preparedStatement.setString(5, lessonEndDate);
            preparedStatement.setString(6, lessonStartDate);
            preparedStatement.setString(7, lessonStartDate);
            preparedStatement.setString(8, lessonEndDate);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) == 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
