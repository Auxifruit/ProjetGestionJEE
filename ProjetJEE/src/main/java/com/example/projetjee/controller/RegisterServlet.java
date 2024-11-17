package com.example.projetjee.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/CYDataBase?useSSL=false&serverTimezone=UTC";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "cytech0001";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirige vers la page d'inscription
        request.getRequestDispatcher("/WEB-INF/jsp/pages/register.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String birthdate = request.getParameter("birthdate");
        String majorId = request.getParameter("major");

        try {
            // Charger le driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connexion à la base de données
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

                // Vérifier si l'email existe déjà
                String checkEmailSql = "SELECT COUNT(*) FROM Users WHERE userEmail = ?";
                PreparedStatement checkEmailStmt = connection.prepareStatement(checkEmailSql);
                checkEmailStmt.setString(1, email);
                ResultSet emailResult = checkEmailStmt.executeQuery();
                if (emailResult.next() && emailResult.getInt(1) > 0) {
                    // Email déjà existant
                    request.setAttribute("error", "Cette adresse e-mail est déjà utilisée.");
                    request.getRequestDispatcher("/WEB-INF/jsp/pages/register.jsp").forward(request, response);
                    return;
                }

                // Insérer l'utilisateur dans la table `Users`
                String insertUserSql = "INSERT INTO Users (userPassword, userLastName, userName, userEmail, userBirthdate, roleId) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement insertUserStmt = connection.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS);
                insertUserStmt.setString(1, password);
                insertUserStmt.setString(2, lastName);
                insertUserStmt.setString(3, firstName);
                insertUserStmt.setString(4, email);
                insertUserStmt.setString(5, birthdate);
                insertUserStmt.setInt(6, 1); // RoleId = 1 pour les étudiants
                insertUserStmt.executeUpdate();

                // Récupérer l'ID généré pour l'utilisateur
                ResultSet generatedKeys = insertUserStmt.getGeneratedKeys();
                int userId = 0;
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                }

                // Insérer l'étudiant dans la table `Student`
                String insertStudentSql = "INSERT INTO Student (studentId, majorId, classId) VALUES (?, ?, NULL)";
                PreparedStatement insertStudentStmt = connection.prepareStatement(insertStudentSql);
                insertStudentStmt.setInt(1, userId);
                insertStudentStmt.setInt(2, Integer.parseInt(majorId));
                insertStudentStmt.executeUpdate();

                // Message de succès
                request.setAttribute("success", "Inscription réussie !");
                request.getRequestDispatcher("/WEB-INF/jsp/pages/register.jsp").forward(request, response);
            }

        } catch (ClassNotFoundException e) {
            throw new ServletException("Pilote JDBC non trouvé", e);
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de l'inscription : " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/pages/register.jsp").forward(request, response);
        }
    }
}
