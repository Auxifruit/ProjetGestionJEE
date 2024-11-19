package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Student;
import com.example.projetjee.model.entities.Users;
import com.example.projetjee.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String STUDENT_TABLE = "etudiant";
    private static final String STUDENT_ID = "idEtudiant";
    private static final String CLASS_ID = "idClasse";

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT * FROM " + STUDENT_TABLE;
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Student student = new Student();
                // Add more fields as per your table structure
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static void deleteStudentById(int studentID) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "DELETE FROM " + STUDENT_TABLE + " WHERE " + STUDENT_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, studentID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addStudentInTable(int studentID, Integer classID) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "INSERT INTO " + STUDENT_TABLE + " VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, studentID);

            if(classID == null) {
                preparedStatement.setNull(2, Types.INTEGER);
            }
            else {
                preparedStatement.setInt(2, classID.intValue());
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // this is an use for a teacher and need a version without teacherID for student or admin i guess
    public static List<Users> getStudentsByDisciplineAndClass(String discipline, String className, int teacherID) {
        List<Users> users = new ArrayList<>();

        // Nouvelle requête SQL qui correspond à la logique donnée
        String query = "SELECT DISTINCT Users.userId, Users.userName, Users.userLastName, Users.userEmail " +
                "FROM Users " +
                "JOIN Student ON Users.userId = Student.studentId " +
                "JOIN Classes ON Student.classesId = Classes.classesId " +
                "JOIN LessonClass ON Classes.classesId = LessonClass.classesId " +
                "JOIN Lesson ON LessonClass.lessonId = Lesson.lessonId " +
                "JOIN Course ON Lesson.courseId = Course.courseId " +
                "WHERE Course.courseName = ? " +
                "AND Classes.classesName = ? " +
                "AND Users.roleId = 1 " + // 1 pour les étudiants
                "AND Lesson.teacherId = ?"; // Pour le professeur spécifié

        try {
            Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            // Définir les paramètres de la requête SQL
            statement.setString(1, discipline);   // Le nom de la discipline (matière)
            statement.setString(2, className);    // Le nom de la classe
            statement.setInt(3, teacherID);       // L'ID du professeur

            // Exécuter la requête
            ResultSet resultSet = statement.executeQuery();

            // Parcourir les résultats et ajouter les étudiants dans la liste
            while (resultSet.next()) {
                Users user = new Users();
                user.setUserId(resultSet.getInt("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserLastName(resultSet.getString("userLastName"));
                user.setUserEmail(resultSet.getString("userEmail"));

                // Ajouter l'utilisateur (étudiant) à la liste
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error fetching students (users): " + e.getMessage());
        }

        return users;
    }

}
