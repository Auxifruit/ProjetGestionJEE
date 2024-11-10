package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Matiere;
import com.example.projetjee.util.DatabaseManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    private static final String SUBJECT_TABLE = "matiere";
    private static final String SUBJECT_ID = "idMatiere";
    private static final String SUBJECT_NAME = "nomMatière";

    public static List<Matiere> getAllSubject() {
        List<Matiere> subjects = new ArrayList<>();
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + SUBJECT_TABLE);

            while (resultSet.next()) {
                Matiere subject = new Matiere();
                subject.setIdMatiere(resultSet.getInt(SUBJECT_ID));
                subject.setNomMatière(resultSet.getString(SUBJECT_NAME));

                subjects.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }
}
