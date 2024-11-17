package com.example.projetjee.controller;

import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.entities.Utilisateur;
import com.example.projetjee.util.DatabaseManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/editInformations")
public class editInformationsServlet extends HttpServlet {
    private static final String USER_EMAIL = "email";
    private static final String USER_LASTNAME = "nom";
    private static final String USER_FIRSTNAME = "prenom";
    private static final String USER_BIRTHDATE = "dateNaissance";
    private static final String USER_ID = "identifiant";
    private static final String USER_PASSWORD = "motDePasse";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les informations du formulaire
        String email = request.getParameter("email");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String dateNaissance = request.getParameter("dateNaissance");
        String identifiant = request.getParameter("identifiant");
        String motDePasse = request.getParameter("motDePasse");

        // ID de l'utilisateur connecté
        String userIdStr = request.getParameter("userId");
        int userId = Integer.parseInt(userIdStr);


        if (email == null || email.trim().isEmpty() || nom == null || nom.trim().isEmpty()) {
            request.setAttribute("message", "Certains champs obligatoires sont vides.");
            request.getRequestDispatcher("/informationsPersonnelles.jsp").forward(request, response);
            return;
        }

        // Connexion à la base de données et mise à jour des informations
        try (Connection connection = DatabaseManager.getConnection()) {
            String sql = "UPDATE Utilisateur SET " + USER_EMAIL + " = ?, "+ USER_LASTNAME +" = ?, "+ USER_FIRSTNAME +" = ?, " +
                    USER_BIRTHDATE +" = ?, "+ USER_PASSWORD +" = ? WHERE "+ USER_ID +" = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);        // Position 1 -> USER_EMAIL
                statement.setString(2, nom);          // Position 2 -> USER_LASTNAME
                statement.setString(3, prenom);       // Position 3 -> USER_FIRSTNAME
                statement.setString(4, dateNaissance);// Position 4 -> USER_BIRTHDATE
                statement.setString(5, motDePasse);   // Position 5 -> USER_PASSWORD
                statement.setInt(6, userId);          // Position 6 -> USER_ID

                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {
                    request.setAttribute("message", "Les informations ont été mises à jour avec succès.");
                } else {
                    request.setAttribute("message", "Échec de la mise à jour des informations.");
                }
            }

        } catch (SQLException e) {
            // Gérer l'exception SQL
            e.printStackTrace();
            request.setAttribute("message", "Erreur lors de la mise à jour des informations : " + e.getMessage());
        }

        // Transmettre uniquement l'userId au JSP
        request.setAttribute("userId", userId);

        // Rediriger vers la page JSP
        request.getRequestDispatcher("/informationsPersonnelles.jsp").forward(request, response);
    }
}
