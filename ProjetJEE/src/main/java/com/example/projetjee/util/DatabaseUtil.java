package com.example.projetjee.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    static final String DB_URL = "jdbc:mysql://localhost/";
    static final String USER = "root";
    static final String PASS = "";
    private static boolean isCreated = false;

    public static void createDatabase() {
        if(isCreated == false) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();

                // 1. Créer la base de données
                String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS cydatabase";
                stmt.executeUpdate(createDatabaseSQL);

                // 2. Utiliser la base de données
                String useDatabaseSQL = "USE cydatabase";
                stmt.executeUpdate(useDatabaseSQL);

                // 3. Créer les tables
                String createMajorTable = "CREATE TABLE Major (" +
                        "majorId INT AUTO_INCREMENT PRIMARY KEY," +
                        " majorName VARCHAR(50) UNIQUE" +
                        ");";
                stmt.executeUpdate(createMajorTable);

                String createUsersTable = "CREATE TABLE Users (" +
                        "userId INT AUTO_INCREMENT PRIMARY KEY," +
                        " userPassword VARCHAR(50)," +
                        " userLastName VARCHAR(50)," +
                        " userName VARCHAR(50)," +
                        " userEmail VARCHAR(50) UNIQUE," +
                        " userBirthdate VARCHAR(50)," +
                        " userRole ENUM('student','teacher','administrator')" +
                        ");";
                stmt.executeUpdate(createUsersTable);

                String createUsersToValidateTable = "CREATE TABLE UsersToValidate (" +
                        "userToValidateId INT AUTO_INCREMENT PRIMARY KEY," +
                        " userToValidatePassword VARCHAR(50)," +
                        " userToValidateLastName VARCHAR(50)," +
                        " userToValidateName VARCHAR(50)," +
                        " userToValidateEmail VARCHAR(50) UNIQUE," +
                        " userToValidateBirthdate VARCHAR(50)," +
                        " userToValidateRole ENUM('student','teacher','administrator')," +
                        " userToValidateMajorId INT," +
                        " FOREIGN KEY (userToValidateMajorId) REFERENCES Major(majorId) ON DELETE SET NULL" +
                        ");";
                stmt.executeUpdate(createUsersToValidateTable);

                String createAdministratorTable = "CREATE TABLE Administrator (" +
                        " administratorId INT," +
                        " FOREIGN KEY (administratorId) REFERENCES Users(userId) ON DELETE CASCADE," +
                        " PRIMARY KEY (administratorId)" +
                        ");";
                stmt.executeUpdate(createAdministratorTable);

                String createTeacherTable = "CREATE TABLE Teacher (" +
                        " teacherId INT," +
                        " FOREIGN KEY (teacherId) REFERENCES Users(userId) ON DELETE CASCADE," +
                        " PRIMARY KEY (teacherId)" +
                        ");";
                stmt.executeUpdate(createTeacherTable);

                String createClassesTable = "CREATE TABLE Classes(" +
                        " classId INT AUTO_INCREMENT PRIMARY KEY," +
                        " className VARCHAR(50) UNIQUE" +
                        ");";
                stmt.executeUpdate(createClassesTable);

                String createStudentTable = "CREATE TABLE Student (" +
                        " studentId INT NOT NULL," +
                        " classId INT, " +
                        " majorId INT," +
                        " FOREIGN KEY (studentId) REFERENCES Users(userId) ON DELETE CASCADE," +
                        " FOREIGN KEY (classId) REFERENCES Classes(classId) ON DELETE SET NULL," +
                        " FOREIGN KEY (majorId) REFERENCES Major(majorId) ON DELETE SET NULL," +
                        " PRIMARY KEY (studentId)" +
                        ");";
                stmt.executeUpdate(createStudentTable);

                String createSubjectsTable = "CREATE TABLE Subjects(" +
                        " subjectId INT AUTO_INCREMENT PRIMARY KEY," +
                        " subjectName VARCHAR(50) UNIQUE" +
                        ");";
                stmt.executeUpdate(createSubjectsTable);

                String createCourseTable = "CREATE TABLE Course(" +
                        " courseId INT AUTO_INCREMENT PRIMARY KEY," +
                        " courseName VARCHAR(50) UNIQUE," +
                        " subjectId INT," +
                        " FOREIGN KEY (subjectId) REFERENCES Subjects(subjectId) ON DELETE SET NULL" +
                        ");";
                stmt.executeUpdate(createCourseTable);

                String createGradeTable = "CREATE TABLE Grade (" +
                        " gradeId INT AUTO_INCREMENT PRIMARY KEY," +
                        " gradeName VARCHAR(50)," +
                        " gradeValue DOUBLE," +
                        " gradeCoefficient INT," +
                        " studentId INT," +
                        " courseId INT," +
                        " teacherId INT," +
                        " FOREIGN KEY (studentId) REFERENCES Student(studentId) ON DELETE CASCADE," +
                        " FOREIGN KEY (courseId) REFERENCES Course(courseId) ON DELETE SET NULL," +
                        " FOREIGN KEY (teacherId) REFERENCES Teacher(teacherId) ON DELETE SET NULL" +
                        ");";
                stmt.executeUpdate(createGradeTable);

                String createLessonTable = "CREATE TABLE Lesson (" +
                        " lessonId INT AUTO_INCREMENT PRIMARY KEY," +
                        " lessonStartDate DATETIME," +
                        " lessonEndDate DATETIME," +
                        " courseId INT," +
                        " teacherId INT," +
                        " FOREIGN KEY (courseId) REFERENCES Course(courseId) ON DELETE CASCADE," +
                        " FOREIGN KEY (teacherId) REFERENCES Teacher(teacherId) ON DELETE SET NULL" +
                        ");";
                stmt.executeUpdate(createLessonTable);

                String createLessonClassTable = "CREATE TABLE LessonClass (" +
                        " lessonClassId INT AUTO_INCREMENT PRIMARY KEY," +
                        " lessonId INT," +
                        " classId INT," +
                        " FOREIGN KEY (lessonId) REFERENCES Lesson(lessonId) ON DELETE CASCADE," +
                        " FOREIGN KEY (classId) REFERENCES Classes(classId) ON DELETE SET NULL" +
                        ");";
                stmt.executeUpdate(createLessonClassTable);

                isCreated = true;
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
