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
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + STUDENT_TABLE);

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
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM " + STUDENT_TABLE + " WHERE " + STUDENT_ID + " = " + studentID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addStudentInTable(int studentID, int classID) {
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO " + STUDENT_TABLE + " VALUES (" + studentID + ", " + classID + ")");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addStudentById(int studentID) {
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO " + STUDENT_TABLE + " VALUES (" + studentID + ", null)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
