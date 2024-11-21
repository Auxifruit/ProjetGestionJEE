package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Student;
import com.example.projetjee.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String STUDENT_TABLE = "student";
    private static final String STUDENT_ID = "studentId";
    private static final String CLASS_ID = "classId";
    private static final String MAJOR_ID = "majorId";

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT * FROM " + STUDENT_TABLE;
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Student student = new Student();
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static void deleteStudentById(int studentID) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "DELETE FROM " + STUDENT_TABLE + " WHERE " + STUDENT_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, studentID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean addStudentInTable(int studentID, Integer classID, Integer majorId) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "INSERT INTO " + STUDENT_TABLE + " VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, studentID);

            if(classID == null) {
                preparedStatement.setNull(2, Types.INTEGER);
            }
            else {
                preparedStatement.setInt(2, classID.intValue());
            }

            if(majorId == null) {
                preparedStatement.setNull(3, Types.INTEGER);
            }
            else {
                preparedStatement.setInt(3, majorId.intValue());
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
