package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Classe;
import com.example.projetjee.model.entities.Seance;
import com.example.projetjee.util.DatabaseManager;
import com.example.projetjee.util.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClasseDAO {
    private static final String CLASS_TABLE = "Classe";
    private static final String CLASS_ID = "idClasse";
    private static final String CLASS_NAME = "nomClasse";

    public static Classe getClasse(int classeId) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT * FROM " + CLASS_TABLE + " WHERE " + CLASS_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, classeId);

            ResultSet resultSet = preparedStatement.executeQuery();

            Classe classe = new Classe();

            if (resultSet.next()) {
                classe.setIdClasse(resultSet.getInt(CLASS_ID));
                classe.setNomClasse(resultSet.getString(CLASS_NAME));
            }
            return classe;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Classe> getAvailableClassesForLesson(int idSeance) {
        List<Classe> availableClasses = new ArrayList<>();

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT c.idClasse, c.nomClasse FROM Classe c WHERE c.idClasse NOT IN (SELECT sc.idClasse FROM SeanceClasse sc WHERE sc.idSeance = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, idSeance);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Classe classe = new Classe();
                int idClasse = resultSet.getInt("idClasse");
                String nomClasse = resultSet.getString("nomClasse");

                classe.setIdClasse(idClasse);
                classe.setNomClasse(nomClasse);

                availableClasses.add(classe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableClasses;
    }
}
