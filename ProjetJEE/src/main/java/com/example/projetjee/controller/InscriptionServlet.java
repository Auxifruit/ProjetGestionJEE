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
import java.sql.SQLException;

@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les données du formulaire
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String dateNaissance = request.getParameter("dateNaissance");
        int idClasse = Integer.parseInt(request.getParameter("classe"));

        // Connexion à la base de données
        String jdbcURL = "jdbc:mysql://localhost:3306/CYDataBase";
        String dbUser = "root";  // Remplace par ton nom d'utilisateur MySQL
        String dbPassword = "cytech0001";  // Remplace par ton mot de passe MySQL

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            // Insérer dans la table Utilisateur
            String sqlUser = "INSERT INTO Utilisateur (motDePasseUtilisateur, nomUtilisateur, prenomUtilisateur, emailUtilisateur, dateDeNaissanceUtilisateur, idRole) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statementUser = connection.prepareStatement(sqlUser, PreparedStatement.RETURN_GENERATED_KEYS);

            statementUser.setString(1, password);
            statementUser.setString(2, nom);
            statementUser.setString(3, prenom);
            statementUser.setString(4, email);
            statementUser.setString(5, dateNaissance);
            statementUser.setInt(6, 1); // 1 correspond au rôle "étudiant"

            statementUser.executeUpdate();

            // Récupérer l'ID de l'utilisateur créé
            var generatedKeys = statementUser.getGeneratedKeys();
            int idUtilisateur = 0;
            if (generatedKeys.next()) {
                idUtilisateur = generatedKeys.getInt(1);
            }

            // Insérer dans la table Étudiant
            String sqlEtudiant = "INSERT INTO Etudiant (idEtudiant, idClasse) VALUES (?, ?)";
            PreparedStatement statementEtudiant = connection.prepareStatement(sqlEtudiant);
            statementEtudiant.setInt(1, idUtilisateur);
            statementEtudiant.setInt(2, idClasse);

            statementEtudiant.executeUpdate();

            // Rediriger vers une page de succès
            response.sendRedirect(request.getContextPath() + "/WEB-INF/jsp/pages/success.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Erreur lors de l'inscription : " + e.getMessage());
        }
    }
}

