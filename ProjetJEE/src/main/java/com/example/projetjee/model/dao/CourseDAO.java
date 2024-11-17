package com.example.projetjee.model.dao;

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
}
