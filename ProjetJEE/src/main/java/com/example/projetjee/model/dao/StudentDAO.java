package com.example.projetjee.model.dao;

import models.Etudiant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/cydatabase";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    public List<Etudiant> getAllStudents() {
        List<Etudiant> students = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Register the driver (optional)
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM etudiant");

            while (resultSet.next()) {
                Etudiant student = new Etudiant();
                student.setEmailEtudiant(resultSet.getString("emailEtudiant"));
                student.setNomEtudiant(resultSet.getString("nomEtudiant"));
                student.setPrénomEtudiant(resultSet.getString("prenomEtudiant"));
                // Add more fields as per your table structure
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return students;
    }
}
