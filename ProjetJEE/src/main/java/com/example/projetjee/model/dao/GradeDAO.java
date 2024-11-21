package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Grade;
import com.example.projetjee.util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GradeDAO {
    // for the database nextID
    private static final String GRADE_TABLE = "Grade";
    private static final String GRADE_COLUMN_ID = "1";
    // for the other configuration
    private static final String GRADE_ID = "gradeId";
    private static final String GRADE_NAME = "gradeName";
    private static final String GRADE_VALUE = "gradeValue";
    private static final String GRADE_COEFFICIENT = "gradeCoefficient";
    private static final String STUDENT_ID = "studentId";
    private static final String COURSE_ID = "courseId";
    private static final String TEACHER_ID = "teacherId";

    public static boolean insertGrade(Grade grade) {

        String query = "INSERT INTO Grade" + "(" +
                GRADE_NAME +", "+ GRADE_VALUE +", "+ GRADE_COEFFICIENT +", "
                + STUDENT_ID +", "+ COURSE_ID +", "+ TEACHER_ID
                + ") VALUES (?,?,?,?,?,?)";

        try {
            Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, "'"+grade.getGradeName()+"'");
            preparedStatement.setString(2, String.valueOf(grade.getGradeValue()));
            preparedStatement.setString(3, String.valueOf(grade.getGradeCoefficient()));
            preparedStatement.setString(4, String.valueOf(grade.getStudentId()));
            preparedStatement.setString(5, String.valueOf(grade.getCourseId()));
            preparedStatement.setString(6, String.valueOf(grade.getTeacherId()));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Une erreur est survenue lors de l'ajout d'une note dans la base de donnée");
            return false;
        }
        return true;
    }
}
