package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Lesson;
import com.example.projetjee.util.DatabaseManager;
import com.example.projetjee.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonDAO {
    private static final String LESSON_TABLE = "Lesson";
    private static final String LESSON_ID = "lessonId";
    private static final String LESSON_START_DATE = "lessonStartDate";
    private static final String LESSON_END_DATE = "lessonEndDate";
    private static final String LESSON_COURSE_ID = "courseId";
    private static final String LESSON_TEACHER_ID = "teacherId";

    public static List<Lesson> getAllLessons() {
        List<Lesson> lessons = new ArrayList<>();
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT * FROM " + LESSON_TABLE;
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Lesson lesson = new Lesson();

                lesson.setLessonId(resultSet.getInt(LESSON_ID));
                lesson.setLessonStartDate(DateUtil.dateStringToTimestamp(resultSet.getString(LESSON_START_DATE)));
                lesson.setLessonEndDate(DateUtil.dateStringToTimestamp(resultSet.getString(LESSON_END_DATE)));
                lesson.setCourseId(resultSet.getInt(LESSON_COURSE_ID));
                lesson.setTeacherId(resultSet.getInt(LESSON_TEACHER_ID));

                lessons.add(lesson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessons;
    }

    public static Lesson getLesson(int LessonId) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT * FROM " + LESSON_TABLE + " WHERE " + LESSON_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, LessonId);

            ResultSet resultSet = preparedStatement.executeQuery();

            Lesson lesson = new Lesson();

            if (resultSet.next()) {
                lesson.setLessonId(resultSet.getInt(LESSON_ID));
                lesson.setLessonStartDate(DateUtil.dateStringToTimestamp(resultSet.getString(LESSON_START_DATE)));
                lesson.setLessonEndDate(DateUtil.dateStringToTimestamp(resultSet.getString(LESSON_END_DATE)));
                lesson.setCourseId(resultSet.getInt(LESSON_COURSE_ID));
                lesson.setTeacherId(resultSet.getInt(LESSON_TEACHER_ID));
            }
            return lesson;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean addLessonInTable(Integer lessonId, String lessonStartDate, String lessonsEndDate, int lessonCourseId, int lessonTeacherId) {
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
            return false;
        }

        return true;
    }

    public static boolean deleteLessonFromTable(int lessonId) {
        if(lessonId <= 0) {
            return false;
        }

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "DELETE FROM " + LESSON_TABLE + " WHERE " + LESSON_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, lessonId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean modifyLessonInTable(int lessonId, String startDate, String endDate, int courseId, int teacherId) {
        if(lessonId <= 0) {
            return false;
        }

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "UPDATE " + LESSON_TABLE + " SET " + LESSON_START_DATE + " = ?, " + LESSON_END_DATE + " = ?," + LESSON_COURSE_ID + " = ?, " + LESSON_TEACHER_ID + " = ? WHERE " + LESSON_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);
            preparedStatement.setInt(3, courseId);
            preparedStatement.setInt(4, teacherId);
            preparedStatement.setInt(5, lessonId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static String getLessonStartDate(int lessonId) {
        String lessonStartDate = null;
        if(lessonId <= 0) {
            return lessonStartDate;
        }

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT " + LESSON_START_DATE + " FROM " + LESSON_TABLE + " WHERE " + LESSON_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, lessonId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                lessonStartDate = resultSet.getString(LESSON_START_DATE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lessonStartDate;
    }

    public static String getLessonEndDate(int lessonId) {
        String lessonEndDate = null;
        if(lessonId <= 0) {
            return lessonEndDate;
        }

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT " + LESSON_END_DATE + " FROM " + LESSON_TABLE + " WHERE " + LESSON_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, lessonId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                lessonEndDate = resultSet.getString(LESSON_END_DATE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lessonEndDate;
    }

    public static int getLessonCourseId(int lessonId) {
        int lessonCourseId = -1;
        if(lessonId <= 0) {
            return lessonCourseId;
        }

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT " + LESSON_COURSE_ID + " FROM " + LESSON_TABLE + " WHERE " + LESSON_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, lessonId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                lessonCourseId = resultSet.getInt(LESSON_COURSE_ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lessonCourseId;
    }

    public static int getLessonTeacherId(int lessonId) {
        int lessonTeacherId = -1;
        if(lessonId <= 0) {
            return lessonTeacherId;
        }

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT " + LESSON_TEACHER_ID + " FROM " + LESSON_TABLE + " WHERE " + LESSON_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, lessonId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                lessonTeacherId = resultSet.getInt(LESSON_TEACHER_ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lessonTeacherId;
    }

    public static boolean isLessonInTable(int lessonId) {
        if(lessonId <= 0) {
            return true;
        }

        boolean isIn = true;

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT COUNT(*) FROM " + LESSON_TABLE + " WHERE " + LESSON_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, lessonId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                isIn = count != 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }

        return isIn;
    }

    public static boolean isLessonPossible(Integer lessonId, int teacherId, String lessonStartDate, String lessonEndDate) {
        boolean isPossible = true;

        String query = "SELECT COUNT(*) FROM " + LESSON_TABLE + " WHERE " + LESSON_TEACHER_ID +" = ? AND (? IS NULL OR " + LESSON_ID + " != ?) AND ("
                + "(" + LESSON_START_DATE + " < ? AND " + LESSON_END_DATE + " > ?) "
                + "OR (" + LESSON_START_DATE + " BETWEEN ? AND ?) "
                + "OR (" + LESSON_END_DATE + " BETWEEN ? AND ?))";

        try {
            Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, teacherId);
            if (lessonId == null) {
                preparedStatement.setNull(2, java.sql.Types.INTEGER); // Aucun ID à exclure
                preparedStatement.setNull(3, java.sql.Types.INTEGER);
            } else {
                preparedStatement.setInt(2, lessonId); // Exclusion de l'ID de la séance
                preparedStatement.setInt(3, lessonId);
            }
            preparedStatement.setString(4, lessonStartDate);
            preparedStatement.setString(5, lessonEndDate);
            preparedStatement.setString(6, lessonStartDate);
            preparedStatement.setString(7, lessonEndDate);
            preparedStatement.setString(8, lessonStartDate);
            preparedStatement.setString(9, lessonEndDate);

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
