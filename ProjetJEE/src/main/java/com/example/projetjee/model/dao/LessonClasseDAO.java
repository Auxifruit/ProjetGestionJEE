package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Classe;
import com.example.projetjee.model.entities.Seance;
import com.example.projetjee.util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonClasseDAO {
    private static String LESSON_CLASS_TABLE = "SeanceClasse";
    private static String LESSON_CLASS_ID = "idSeanceClasse";
    private static String LESSON_ID = "idSeance";
    private static String CLASS_ID = "idClasse";
    public static List<Classe> getLessonClasses(int lessonId) {
        List<Classe> availableClasses = new ArrayList<>();

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT " + CLASS_ID + " FROM " + LESSON_CLASS_TABLE + " WHERE " + LESSON_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, lessonId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idClasse = resultSet.getInt("idClasse");

                availableClasses.add(ClasseDAO.getClasse(idClasse));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableClasses;
    }

    public static boolean addLessonClassInTable(int lessonId, int classId) {
        if(lessonId <= 0 || classId <= 0) {
            return false;
        }

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "INSERT INTO " + LESSON_CLASS_TABLE + "(" + LESSON_ID + ", " + CLASS_ID + ") VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, lessonId);
            preparedStatement.setInt(2, classId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteLessonClassInTable(int lessonId, int classId) {
        if(lessonId <= 0 || classId <= 0) {
            return false;
        }

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "DELETE FROM " + LESSON_CLASS_TABLE + " WHERE " + LESSON_ID + " = ? AND " + CLASS_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, lessonId);
            preparedStatement.setInt(2, classId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean canClassParticipate(int idClasse, int idSeance) {
        Seance lesson = LessonDAO.getLesson(idSeance);

        if (lesson == null) {
            return false;
        }

        String lessonStartDate = lesson.getDateDebutSeance().toString();
        String lessonEndDate = lesson.getDateFinSeance().toString();

        String query = "SELECT COUNT(*) FROM SeanceClasse sc JOIN Seance s ON sc.idSeance = s.idSeance WHERE sc.idClasse = ? AND s.idSeance != ? AND ( (s.dateDebutSeance < ? AND s.dateFinSeance > ?) OR (s.dateDebutSeance < ? AND s.dateFinSeance > ?) OR (s.dateDebutSeance >= ? AND s.dateFinSeance <= ?)); ";

        try {
            Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, idClasse);
            preparedStatement.setInt(2, idSeance);
            preparedStatement.setString(3, lessonEndDate);
            preparedStatement.setString(4, lessonStartDate);
            preparedStatement.setString(5, lessonEndDate);
            preparedStatement.setString(6, lessonStartDate);
            preparedStatement.setString(7, lessonStartDate);
            preparedStatement.setString(8, lessonEndDate);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) == 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
