package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Etudiant;
import com.example.projetjee.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String STUDENT_TABLE = "etudiant";
    private static final String STUDENT_ID = "idEtudiant";
    private static final String CLASS_ID = "idClasse";

    public List<Etudiant> getAllStudents() {
        List<Etudiant> students = new ArrayList<>();
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT * FROM " + STUDENT_TABLE;
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Etudiant student = new Etudiant();
                // Add more fields as per your table structure
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

    public static void addStudentInTable(int studentID, Integer classID) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "INSERT INTO " + STUDENT_TABLE + " VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, studentID);

            if(classID == null) {
                preparedStatement.setNull(2, Types.INTEGER);
            }
            else {
                preparedStatement.setInt(2, classID.intValue());
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
