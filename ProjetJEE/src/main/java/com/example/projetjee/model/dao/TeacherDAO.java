package com.example.projetjee.model.dao;

import com.example.projetjee.util.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TeacherDAO {
    private static final String TEACHER_TABLE = "enseignant";
    private static final String TEACHER_ID = "idEnseignant";

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
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO " + TEACHER_TABLE + " VALUES (" + teacherID + ")");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
