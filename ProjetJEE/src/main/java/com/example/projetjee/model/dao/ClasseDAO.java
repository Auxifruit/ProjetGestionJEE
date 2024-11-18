package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Classes;
import com.example.projetjee.model.entities.Lesson;
import com.example.projetjee.util.DatabaseManager;
import com.example.projetjee.util.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClasseDAO {
    private static final String CLASS_TABLE = "Classes";
    private static final String CLASS_ID = "classId";
    private static final String CLASS_NAME = "className";

    public static Classes getClasse(int classeId) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT * FROM " + CLASS_TABLE + " WHERE " + CLASS_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, classeId);

            ResultSet resultSet = preparedStatement.executeQuery();

            Classes classe = new Classes();

            if (resultSet.next()) {
                classe.setClassId(resultSet.getInt(CLASS_ID));
                classe.setClassName(resultSet.getString(CLASS_NAME));
            }
            return classe;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Classes> getAvailableClassesForLesson(int idLesson) {
        List<Classes> availableClasses = new ArrayList<>();

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT c.idClasse, c.nomClasse FROM Classe c WHERE c.idClasse NOT IN (SELECT sc.idClasse FROM LessonClasse sc WHERE sc.idLesson = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, idLesson);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Classes classe = new Classes();
                int idClasse = resultSet.getInt("idClasse");
                String nomClasse = resultSet.getString("nomClasse");

                classe.setClassId(idClasse);
                classe.setClassName(nomClasse);

                availableClasses.add(classe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableClasses;
    }
}
