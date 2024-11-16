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

@WebServlet("/modifierInformations")
public class ModifierUtilisateurServlet extends HttpServlet {

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
        int userId = (int) request.getSession().getAttribute("userId");

        // Connexion à la base de données et mise à jour des informations
        try (Connection connection = DatabaseManager.getConnection()) {
            String sql = "UPDATE Utilisateur SET emailUtilisateur = ?, nomUtilisateur = ?, prenomUtilisateur = ?, " +
                    "dateNaissanceUtilisateur = ?, identifiantUtilisateur = ?, motDePasseUtilisateur = ? WHERE idUtilisateur = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                statement.setString(2, nom);
                statement.setString(3, prenom);
                statement.setString(4, dateNaissance);
                statement.setString(5, identifiant);
                statement.setString(6, motDePasse);
                statement.setInt(7, userId);

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
