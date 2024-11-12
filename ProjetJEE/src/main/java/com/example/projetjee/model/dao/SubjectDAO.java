package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Matiere;
import com.example.projetjee.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    private static final String SUBJECT_TABLE = "matiere";
    private static final String SUBJECT_ID = "idMatiere";
    private static final String SUBJECT_NAME = "nomMatiere";

    public static List<Matiere> getAllSubject() {
        List<Matiere> subjects = new ArrayList<>();
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT * FROM " + SUBJECT_TABLE;
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Matiere subject = new Matiere();
                subject.setIdMatiere(resultSet.getInt(SUBJECT_ID));
                subject.setNomMatiere(resultSet.getString(SUBJECT_NAME));

                subjects.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    public static boolean addSubjectInTable(String subjectName) {
        if(subjectName == null || subjectName.isEmpty()) {
            return false;
        }

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "INSERT INTO " + SUBJECT_TABLE + "(" + SUBJECT_NAME + ") VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, subjectName);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Une erreur est survenue lors de l'ajout d'une matière dans la base de donnée");
            return false;
        }
        return true;
    }

    public static boolean deleteSubjectFromTable(int subjectId) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "DELETE FROM " + SUBJECT_TABLE + " WHERE " + SUBJECT_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, subjectId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Une erreur est survenue lors de la suppression de la matière de la base de donnée");
            return false;
        }
        return true;
    }

    public static boolean modifySubjectFromTable(int subjectId, String subjectNewName) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "UPDATE " + SUBJECT_TABLE + " SET " + SUBJECT_NAME + " = ? WHERE " + SUBJECT_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, subjectNewName);
            preparedStatement.setInt(2, subjectId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Une erreur est survenue lors de la modification de la matière de la base de donnée");
            return false;
        }
        return true;
    }

    public static String getSubjectNameById(int subjectId) {
        String subjectName = " ";

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT " + SUBJECT_NAME + " FROM " + SUBJECT_TABLE + " WHERE " + SUBJECT_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, subjectId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                subjectName = resultSet.getString(SUBJECT_NAME);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjectName;
    }

    public static boolean isSubjectInTable(String subjectName) {
        if(subjectName == null || subjectName.isEmpty()) {
            return false;
        }
        boolean isIn = true;

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT COUNT(*) FROM " + SUBJECT_TABLE + " WHERE " + SUBJECT_NAME + " LIKE(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, subjectName);

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
