package com.example.projetjee.controller;

import com.example.projetjee.model.entities.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.projetjee.model.dao.UserDAO;

import java.io.IOException;

@WebServlet("/editInformations")
public class EditInformationsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les informations du formulaire
        String email = request.getParameter("email");
        String lastName = request.getParameter("nom");
        String name = request.getParameter("prenom");
        String birthDate = request.getParameter("dateNaissance");
        String password = request.getParameter("motDePasse");

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
        user.setUserPassword(password);
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