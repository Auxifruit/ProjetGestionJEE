package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Cours;
import com.example.projetjee.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    private static final String COURSE_TABLE = "cours";
    private static final String COURSE_ID = "idCours";
    private static final String COURSE_NAME = "nomCours";
    private static final String COURSE_SUBJECT_ID = "idMatiere";

    public static List<Cours> getAllCourses() {
        List<Cours> courses = new ArrayList<>();
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT * FROM " + COURSE_TABLE;
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Cours course = new Cours();
                course.setIdCours(resultSet.getInt(COURSE_ID));
                course.setNomCours(resultSet.getString(COURSE_NAME));
                course.setIdMatiere(resultSet.getInt(COURSE_SUBJECT_ID));

                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public static boolean addCourseInTable(String subjectName, int subjectId) {
        if(subjectName == null || subjectName.isEmpty() || subjectId <= 0) {
            return false;
        }

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "INSERT INTO " + COURSE_TABLE + "(" + COURSE_NAME + ", " + COURSE_SUBJECT_ID + ") VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, subjectName);
            preparedStatement.setInt(2, subjectId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Une erreur est survenue lors de l'ajout d'un cours dans la base de donnée");
            return false;
        }
        return true;
    }

    public static boolean deleteCourseFromTable(int courseId) {
        if(courseId <= 0) {
            return false;
        }

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "DELETE FROM " + COURSE_TABLE + " WHERE " + COURSE_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, courseId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Une erreur est survenue lors de la suppression d'un cours dans la base de donnée");
            return false;
        }
        return true;
    }

    public static boolean modifyCourseInTable(int courseId, String newCourseName, int newCourseSubjectIdString) {
        if (courseId <= 0 || (newCourseName == null || newCourseName.isEmpty()) || newCourseSubjectIdString <= 0) {
            return false;
        }

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "UPDATE " + COURSE_TABLE + " SET " + COURSE_NAME + " = ?, " + COURSE_SUBJECT_ID + " = ? WHERE " + COURSE_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, newCourseName);
            preparedStatement.setInt(2, newCourseSubjectIdString);
            preparedStatement.setInt(3, courseId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Une erreur est survenue lors de la modification d'un cours dans la base de donnée");
            return false;
        }
        return true;
    }

    public static String getCourseName(int courseId) {
        String courseName = null;
        if(courseId <= 0) {
            return courseName;
        }

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT " + COURSE_NAME + " FROM " + COURSE_TABLE + " WHERE " + COURSE_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, courseId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                courseName = resultSet.getString(COURSE_NAME);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseName;
    }

    public static int getCourseSubjectId(int courseId) {
        int courseSubjectId = -1;
        if(courseId <= 0) {
            return courseSubjectId;
        }

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT " + COURSE_SUBJECT_ID + " FROM " + COURSE_TABLE + " WHERE " + COURSE_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, courseId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                courseSubjectId = resultSet.getInt(COURSE_SUBJECT_ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseSubjectId;
    }

    public static boolean isCourseInTable(String courseName, int subjectId) {
        if(courseName == null || courseName.isEmpty() || subjectId <= 0) {
            return true;
        }
        boolean isIn = true;

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT COUNT(*) FROM " + COURSE_TABLE + " WHERE " + COURSE_NAME + " LIKE(?) AND " + COURSE_SUBJECT_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, courseName);
            preparedStatement.setInt(2, subjectId);

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

    public static boolean isCourseInTable(int courseId) {
        if(courseId <= 0) {
            return false;
        }
        boolean isIn = true;

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT COUNT(*) FROM " + COURSE_TABLE + " WHERE " + COURSE_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, courseId);

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
}
