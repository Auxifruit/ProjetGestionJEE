package com.example.projetjee.controller;

import com.example.projetjee.model.entities.Users;
import com.example.projetjee.util.HashPswdUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.projetjee.model.dao.UserDAO;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.projetjee.util.HashPswdUtil.hashPassword;

/**
 * Servlet implementation for editing user information.
 * This servlet processes updates to a user's information such as email, name, and password.
 */
@WebServlet("/editInformations")
public class EditInformationsServlet extends HttpServlet {
    /**
     * Handles the POST request to update the user information in the database.
     * It retrieves the data from the request, validates the inputs, and updates the user's information.
     *
     * @param request the HttpServletRequest containing form data
     * @param response the HttpServletResponse to send the result
     * @throws ServletException if an error occurs during servlet processing
     * @throws IOException if an input or output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve user input from the form
        String email = request.getParameter("email");
        String lastName = request.getParameter("nom");
        String name = request.getParameter("prenom");
        String birthDate = request.getParameter("dateNaissance");
        String password = request.getParameter("motDePasse");
        String hashedPassword = HashPswdUtil.hashPassword(password);

        // Get the user ID from the request
        String userIdStr = request.getParameter("userId");
        int userId;

        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("message", "ID utilisateur invalide.");
            request.getRequestDispatcher("WEB-INF/jsp/pages/personalInformation.jsp").forward(request, response);
            return;
        }

        // Validate required fields: email and last name
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

        // Attempt to update the user information in the database
        String error = UserDAO.modifyUserFromTable(user);

        if (error == null) {
            request.setAttribute("message", "Les informations ont été mises à jour avec succès.");
        } else {
            request.setAttribute("message", error);
        }

        // Forward to the JSP page to display the result
        request.getRequestDispatcher("WEB-INF/jsp/pages/personalInformation.jsp").forward(request, response);
    }


}
