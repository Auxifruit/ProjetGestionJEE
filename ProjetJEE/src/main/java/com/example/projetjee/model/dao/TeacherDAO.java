package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Enseignant;
import com.example.projetjee.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    private static final String TEACHER_TABLE = "enseignant";
    private static final String TEACHER_ID = "idEnseignant";

    public static List<Enseignant> getAllTeachers() {
        List<Enseignant> teacherList = new ArrayList<>();
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM " + TEACHER_TABLE;

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Enseignant teacher = new Enseignant();
                teacher.setIdEnseignant(resultSet.getInt(TEACHER_ID));

                teacherList.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teacherList;
    }

    public static void deleteTeacherById(int teacherID) {
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM " + TEACHER_TABLE + " WHERE " + TEACHER_ID + " = " + teacherID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addTeacherInTable(int teacherID) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "INSERT INTO " + TEACHER_TABLE + " VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, teacherID);
            
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getAllDiscipline(int teacherID) {
        List<String> discipline = new ArrayList<>();
        // la query pour chercher les matières en passant par "Seance"
        String query = "SELECT DISTINCT Cours.nomCours " +
                "FROM Cours " +
                "JOIN Seance ON Cours.idCours = Seance.idCours " +
                "JOIN Enseignant ON Seance.idEnseignant = Enseignant.idEnseignant " +
                "WHERE Enseignant.idEnseignant = ?"; // le ? sera le teacherID associé au 1 du preparedStatement
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, teacherID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                discipline.add(resultSet.getString("nomCours"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return discipline;
    }

    public static List<String> getAllClasses(int teacherID) {
        List<String> classList = new ArrayList<>();
        String query = "SELECT c.nomClasse " +
                "FROM Seance s " +
                "JOIN SeanceClasse sc ON s.idSeance = sc.idSeance " +
                "JOIN Classe c ON sc.idClasse = c.idClasse " +
                "WHERE s.idEnseignant = ?";  // TeacherID param

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, teacherID);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                classList.add(resultSet.getString("nomClasse"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classList;
    }
}
