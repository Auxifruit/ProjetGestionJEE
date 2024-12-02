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

/**
 * Servlet implementation for editing user information.
 */
@WebServlet("/editInformations")
public class EditInformationsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve user input from the form
        String email = request.getParameter("email");
        String lastName = request.getParameter("nom");
        String name = request.getParameter("prenom");
        String birthDate = request.getParameter("dateNaissance");
        String password = request.getParameter("motDePasse");
        String originalPassword = request.getParameter("originalPassword"); // Mot de passe initial

        // Get the user ID from the request
        int userId = Integer.parseInt(request.getParameter("userId"));

        // Retrieve the user from the database
        Users user = UserDAO.getUserById(userId);

        // Update the user object with new data
        user.setUserLastName(lastName);
        user.setUserName(name);
        user.setUserEmail(email);
        user.setUserBirthdate(birthDate);

        // Update the password only if it has been changed
        if (!password.equals(originalPassword)) {
            String hashedPassword = HashPswdUtil.hashPassword(password); // Hash the new password
            user.setUserPassword(hashedPassword);
        }

        // Attempt to update the user information in the database
        UserDAO.modifyUserFromTable(user);

        // Redirect to the JSP page
        request.setAttribute("message", "Les informations ont été mises à jour avec succès.");
        request.getRequestDispatcher("WEB-INF/jsp/pages/personalInformation.jsp").forward(request, response);
    }
}
