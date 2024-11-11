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
}
