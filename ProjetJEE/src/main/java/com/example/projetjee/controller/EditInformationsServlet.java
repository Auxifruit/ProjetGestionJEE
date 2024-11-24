package com.example.projetjee.controller;

import com.example.projetjee.model.entities.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.projetjee.model.dao.UserDAO;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet("/editInformations")
public class EditInformationsServlet extends HttpServlet {

    public static String hashPassword(String password) {
        try {
            // Créer une instance de MessageDigest pour SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Convertir le mot de passe en tableau de bytes
            byte[] encodedHash = digest.digest(password.getBytes());

            // Convertir le tableau de bytes en chaîne hexadécimale
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // Renvoyer une version tronquée à 50 caractères
            return hexString.substring(0, 49);

        } catch (NoSuchAlgorithmException e) {
            // Gérer l'erreur si l'algorithme n'est pas disponible
            throw new RuntimeException("Erreur : Algorithme SHA-256 non disponible", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les informations du formulaire
        String email = request.getParameter("email");
        String lastName = request.getParameter("nom");
        String name = request.getParameter("prenom");
        String birthDate = request.getParameter("dateNaissance");
        String password = request.getParameter("motDePasse");
        String hashedPassword = hashPassword(password);

        // ID de l'utilisateur connecté
        String userIdStr = request.getParameter("userId");
        int userId;

        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("message", "ID utilisateur invalide.");
            request.getRequestDispatcher("WEB-INF/jsp/pages/personalInformation.jsp").forward(request, response);
            return;
        }

        // Validation des champs obligatoires
        if (email == null || email.trim().isEmpty() || lastName == null || lastName.trim().isEmpty()) {
            request.setAttribute("message", "Certains champs obligatoires sont vides.");
            request.getRequestDispatcher("WEB-INF/jsp/pages/personalInformation.jsp").forward(request, response);
            return;
        }

        Users user = UserDAO.getUserById(userId);
        user.setUserPassword(hashedPassword);
        user.setUserLastName(lastName);
        user.setUserName(name);
        user.setUserEmail(email);
        user.setUserBirthdate(birthDate);

        // Mise à jour des informations dans la base de données
        String error = UserDAO.modifyUserFromTable(user);

        if (error == null) {
            request.setAttribute("message", "Les informations ont été mises à jour avec succès.");
        } else {
            request.setAttribute("message", error);
        }

        // Rediriger vers la page JSP
        request.getRequestDispatcher("WEB-INF/jsp/pages/personalInformation.jsp").forward(request, response);
    }


}
