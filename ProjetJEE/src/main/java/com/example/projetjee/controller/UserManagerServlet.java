package com.example.projetjee.controller;

import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.entities.Role;
import com.example.projetjee.model.entities.Users;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
/**
 * Servlet responsible for managing users in the system.
 * It retrieves a list of users from the database and filters them based on the role provided in the request.
 * This servlet handles both GET and POST requests for user management.
 */
@WebServlet(name = "userManagerServlet", value = "/userManager-servlet")
public class UserManagerServlet extends HttpServlet {
    /**
     * Handles GET requests to retrieve users from the database and display them.
     * If a role filter is provided in the request parameters, the users are filtered by their role.
     * The list of users is then set as a request attribute for display in the user management page.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String roleFilterString = request.getParameter("roleFilter");
        Role roleFilter = null;
        // If a role filter is provided, convert it to a Role enum
        if(roleFilterString != null && !roleFilterString.isEmpty()) {
            roleFilter = Role.valueOf(roleFilterString);
        }
        // Get the list of users filtered by the specified role
        List<Users> usersList = UserDAO.getAllUsersByFilter(roleFilter);

        request.setAttribute("users", usersList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/userManager.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles POST requests by forwarding them to the doGet method.
     * This is a convenience method to avoid code duplication for handling POST requests in the same way as GET requests.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

}
