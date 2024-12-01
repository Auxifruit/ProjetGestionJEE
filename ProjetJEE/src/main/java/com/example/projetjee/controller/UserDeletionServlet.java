package com.example.projetjee.controller;

import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.entities.Role;
import com.example.projetjee.model.entities.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
/**
 * Servlet responsible for handling the deletion of users from the system.
 * It allows an administrator to delete a user based on the user's ID.
 * The servlet ensures that the logged-in user cannot delete their own account
 * and verifies that the user exists before attempting deletion.
 */
@WebServlet(name = "userDeletionServlet", value = "/userDeletion-servlet")
public class UserDeletionServlet extends HttpServlet {
    /**
     * Handles GET requests by forwarding them to the POST method for processing.
     * This is a convenience method to avoid code duplication.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     * @throws ServletException if the request cannot be processed
     * @throws IOException if an input or output error occurs during the request processing
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles POST requests to delete a user from the system.
     * It verifies if the user is allowed to delete the specified user based on the following conditions:
     * - The logged-in user cannot delete their own account.
     * - The user to be deleted must exist in the system.
     * - If the user exists, they are deleted, and the user manager page is refreshed.
     * - If there are any issues, an error message is displayed.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     * @throws ServletException if the request cannot be processed
     * @throws IOException if an input or output error occurs during the request processing
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer connectedUserId = (Integer) session.getAttribute("user");
        String userIdString = request.getParameter("userId");

        if(userIdString == null || userIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un utilisateur.");
            request.getRequestDispatcher("userManager-servlet?roleFilter=student").forward(request, response);
            return;
        }

        int userId = Integer.parseInt(userIdString);
        Users user = UserDAO.getUserById(userId);
        Role role = user.getUserRole();

        // Ensure that the logged-in user is not trying to delete their own account
        if(connectedUserId.equals(userId)) {
            request.setAttribute("erreur", "Erreur : Vous ne pouvez pas vous supprimer vous-même.");
            request.getRequestDispatcher("userManager-servlet?roleFilter="+role).forward(request, response);
            return;
        }
        // Check if the user exists
        if(user == null) {
            request.setAttribute("erreur", "Erreur : L'utilisateur n'existe pas.");
            request.getRequestDispatcher("userManager-servlet?roleFilter="+role).forward(request, response);
            return;
        }
        // Delete the user from the database
        if(UserDAO.deleteUserFromTable(userId) == true) {
            request.getRequestDispatcher("userManager-servlet?roleFilter="+role).forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la suppression de l'utilisateur.");
            request.getRequestDispatcher("userManager-servlet?roleFilter="+role).forward(request, response);
        }

    }
}
