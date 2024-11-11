package com.example.projetjee.model.dao;

import com.example.projetjee.util.DatabaseManager;

import java.sql.*;

public class LessonDAO {
    private static final String LESSON_TABLE = "seance";
    private static final String LESSON_ID = "idSeance";
    private static final String LESSON_START_DATE = "dateDebutSeance";
    private static final String LESSON_END_DATE = "dateFinSeance";
    private static final String LESSON_COURSE_ID = "idCours";
    private static final String LESSON_TEACHER_ID = "idEnseignant";

    public static void AddLesson(Integer lessonId, String lessonStartDate, String lessonsEndDate, int lessonCourseId, int lessonTeacherId) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "INSERT INTO " + LESSON_TABLE + " VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            if(lessonId == null) {
                preparedStatement.setNull(1, Types.INTEGER);
            }
            else {
                preparedStatement.setInt(1, lessonId.intValue());
            }

            preparedStatement.setString(2, lessonStartDate);
            preparedStatement.setString(3, lessonsEndDate);
            preparedStatement.setInt(4, lessonCourseId);
            preparedStatement.setInt(5, lessonTeacherId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isLessonPossible(int teacherId, String lessonStartDate, String lessonEndDate) {
        boolean isPossible = true;

        String query = "SELECT COUNT(*) FROM " + LESSON_TABLE + " WHERE " + LESSON_TEACHER_ID +" = ? AND ("
                + "(" + LESSON_START_DATE + " < ? AND " + LESSON_END_DATE + " > ?) "
                + "OR (" + LESSON_START_DATE + " BETWEEN ? AND ?) "
                + "OR (" + LESSON_END_DATE + " BETWEEN ? AND ?))";

        try {
            Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, teacherId);
            preparedStatement.setString(2, lessonStartDate);
            preparedStatement.setString(3, lessonEndDate);
            preparedStatement.setString(4, lessonStartDate);
            preparedStatement.setString(5, lessonEndDate);
            preparedStatement.setString(6, lessonStartDate);
            preparedStatement.setString(7, lessonEndDate);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                isPossible = count == 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return isPossible;
    }
}
