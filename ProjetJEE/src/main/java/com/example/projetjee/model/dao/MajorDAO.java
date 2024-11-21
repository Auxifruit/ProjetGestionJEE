package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Major;
import com.example.projetjee.model.entities.Subjects;
import com.example.projetjee.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MajorDAO {
    private static final String MAJOR_TABLE = "major";
    private static final String MAJOR_ID = "majorId";
    private static final String MAJOR_NAME = "majorName";

    public static List<Major> getAllMajor() {
        List<Major> majors = new ArrayList<>();
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT * FROM " + MAJOR_TABLE;
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Major major = new Major();

                major.setMajorId(resultSet.getInt(MAJOR_ID));
                major.setMajorName(resultSet.getString(MAJOR_NAME));

                majors.add(major);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return majors;
    }

    public static Major getMajor(int majorId) {
        if(majorId <= 0) {
            return null;
        }

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT * FROM " + MAJOR_TABLE + " WHERE " + MAJOR_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, majorId);

            ResultSet resultSet = preparedStatement.executeQuery();

            Major major = new Major();

            if (resultSet.next()) {
                major.setMajorId(resultSet.getInt(MAJOR_ID));
                major.setMajorName(resultSet.getString(MAJOR_NAME));
            }
            return major;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean addMajorInTable(String majorName) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "INSERT INTO " + MAJOR_TABLE + " VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setString(2, majorName);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteMajorFromTable(int majorId) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "DELETE FROM " + MAJOR_TABLE + " WHERE " + MAJOR_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, majorId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean modifyMajorFromTable(int majorId, String majorNewName) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "UPDATE " + MAJOR_TABLE + " SET " + MAJOR_NAME + " = ? WHERE " + MAJOR_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, majorNewName);
            preparedStatement.setInt(2, majorId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Une erreur est survenue lors de la modification de la filière de la base de donnée");
            return false;
        }
        return true;
    }

    public static String getMajorNameById(int majorId) {
        if(majorId <= 0) {
            return null;
        }

        String majorName = null;

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT " + MAJOR_NAME +" FROM " + MAJOR_TABLE + " WHERE " + MAJOR_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, majorId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                majorName = resultSet.getString(MAJOR_NAME);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return majorName;
    }

    public static boolean isMajorInTable(String majorName) {
        if(majorName == null || majorName.isEmpty()) {
            return false;
        }
        boolean isIn = true;

        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT COUNT(*) FROM " + MAJOR_TABLE + " WHERE " + MAJOR_NAME + " LIKE(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, majorName);

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
