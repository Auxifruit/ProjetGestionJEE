package com.example.projetjee.model.dao;

import com.example.projetjee.util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class LessonDAO {
    private static final String LESSON_TABLE = "seance";
    private static final String LESSON_ID = "idSeance";
    private static final String LESSON_START_DATE = "dateDebutCours";
    private static final String LESSON_END_DATE = "dateFin";
    private static final String LESSON_COURSE_ID = "idCours";
    private static final String LESSON_TEACHER_ID = "idEnseignant";

    public static void AddLesson(Integer lessonId, String lessonStartDate, String lessonsEndDate, int lessonCourseId, int lessonTeacherId) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "INSERT INTO " + LESSON_TABLE + " VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, lessonId);
            preparedStatement.setString(2, lessonStartDate);
            preparedStatement.setString(3, lessonsEndDate);
            preparedStatement.setInt(4, lessonCourseId);
            preparedStatement.setInt(5, lessonTeacherId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
