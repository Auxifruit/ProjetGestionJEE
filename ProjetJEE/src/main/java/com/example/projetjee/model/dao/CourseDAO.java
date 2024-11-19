package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Course;
import com.example.projetjee.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    private static final String COURSE_TABLE = "course";
    private static final String COURSE_ID = "idCours";
    private static final String COURSE_NAME = "nomCours";
    private static final String COURSE_SUBJECT_ID = "idMatiere";

    public static List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT * FROM " + COURSE_TABLE;
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Course course = new Course();
                course.setCourseId(resultSet.getInt(COURSE_ID));
                course.setCourseName(resultSet.getString(COURSE_NAME));
                course.setSubjectId(resultSet.getInt(COURSE_SUBJECT_ID));

                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
}
