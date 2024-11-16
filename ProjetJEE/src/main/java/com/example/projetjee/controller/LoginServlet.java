package com.example.projetjee.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/CYDataBase?useSSL=false&serverTimezone=UTC";
    private static final String DB_USERNAME = "root"; // Change en fonction de ton utilisateur MySQL
    private static final String DB_PASSWORD = "cytech0001";     // Ajoute ton mot de passe MySQL

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirige vers la page de connexion
        request.getRequestDispatcher("/WEB-INF/jsp/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Charger explicitement le driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établir la connexion à la base de données
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                String sql = "SELECT * FROM Utilisateur WHERE emailUtilisateur = ? AND motDePasseUtilisateur = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, email);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    // Authentification réussie
                    request.getSession().setAttribute("user", resultSet.getString("prenomUtilisateur"));
                    response.sendRedirect("dashboard");
                } else {
                    // Authentification échouée
                    request.setAttribute("error", "Email ou mot de passe incorrect !");
                    request.getRequestDispatcher("/WEB-INF/jsp/pages/login.jsp").forward(request, response);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new ServletException("Pilote JDBC non trouvé", e);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la connexion à la base de données", e);
        }
    }

}
