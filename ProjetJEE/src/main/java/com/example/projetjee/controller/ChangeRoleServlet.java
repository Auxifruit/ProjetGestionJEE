package com.example.projetjee.controller;

import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.entities.Role;
import com.example.projetjee.model.entities.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
/**
 * Servlet that handles the user role management functionality.
 * This servlet allows for filtering users by role and updating the roles of users.
 */
@WebServlet(name = "changeRoleServlet", value = "/changeRole-servlet")
public class ChangeRoleServlet extends HttpServlet {
    /**
     * Handles GET requests to display the list of users, optionally filtered by role.
     * If a role filter is provided, it retrieves the users associated with that role.
     *
     * @param request  the HttpServletRequest containing the request data
     * @param response the HttpServletResponse to send the response to the client
     * @throws IOException if an input or output exception occurs
     * @throws ServletException if the request cannot be processed
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String roleFilterString = request.getParameter("roleFilter");
        Role roleFilter = null;
        if(roleFilterString != null && !roleFilterString.isEmpty()) {
            roleFilter = Role.valueOf(roleFilterString);
        }

        List<Users> usersList = UserDAO.getAllUsersByFilter(roleFilter);
        // Set the list of users as a request attribute to be accessed in the JSP
        request.setAttribute("users", usersList);

        // Forward the request to the roleList.jsp page for displaying the users
        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/roleList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Handles POST requests to update the role of a user.
     * The new role is validated before applying the change to the database.
     *
     * @param request  the HttpServletRequest containing the request data
     * @param response the HttpServletResponse to send the response to the client
     * @throws ServletException if the request cannot be processed
     * @throws IOException if an input or output exception occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Role newUserRole = Role.valueOf(request.getParameter("newRoleID"));
        String userParam = request.getParameter("user");

        if (userParam == null || userParam.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un Users.");
            doGet(request, response);
        }

        int userId = Integer.parseInt(userParam);
        Users user = UserDAO.getUserById(userId);

        if (newUserRole.equals(user.getUserRole())) {
            request.setAttribute("erreur", "Erreur : L'Users a déjà ce rôle.");
            doGet(request, response);
        }
        // Attempt to modify the user's role in the database

        if(UserDAO.modifyUserRole(user, user.getUserRole(), newUserRole) == true) {
            doGet(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Une erreur est survenue lors de la modification du rôle.");
            doGet(request, response);
        }
    }
}
