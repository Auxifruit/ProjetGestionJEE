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


    public static List<Users> getStudentsByDisciplineAndClass(String discipline, String className, int teacherID) {
        List<Users> users = new ArrayList<>();

        // Query to fetch users (students) by joining the necessary tables
        String query = "SELECT DISTINCT User.userId, User.firstName, User.lastName " +
                "FROM User " +
                "JOIN Enrollment ON User.userId = Enrollment.userId " +
                "JOIN Class ON Enrollment.classId = Class.classId " +
                "JOIN LessonClass ON Class.classId = LessonClass.classId " +
                "JOIN Lesson ON LessonClass.lessonId = Lesson.lessonId " +
                "JOIN Course ON Lesson.courseId = Course.courseId " +
                "WHERE Course.courseName = ? AND Class.className = ? " +
                "AND User.roleId = 1 AND Lesson.teacherId = ?"; // roleId = 1 for students, filter by teacherID

        try {
            Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            // Set the parameters for the query
            statement.setString(1, discipline);
            statement.setString(2, className);
            statement.setInt(3, teacherID); // Set the teacher ID as the 3rd parameter

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Populate the list of users (students)
            while (resultSet.next()) {
                Users user = new Users();
                user.setUserId(resultSet.getInt("userId"));
                user.setUserName(resultSet.getString("firstName"));
                user.setUserLastName(resultSet.getString("lastName"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error fetching students (users): " + e.getMessage());
        }

        return users;
    }

}
