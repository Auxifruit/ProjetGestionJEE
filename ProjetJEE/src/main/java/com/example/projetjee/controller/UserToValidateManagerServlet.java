package com.example.projetjee.controller;

import com.example.projetjee.model.dao.UserToValidateDAO;
import com.example.projetjee.model.entities.Userstovalidate;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
/**
 * Servlet responsible for managing users who are pending validation.
 * It retrieves the list of users to validate and displays them in the userToValidateManager.jsp page.
 */
@WebServlet(name = "userToValidateManagerServlet", value = "/userToValidateManager-servlet")
public class UserToValidateManagerServlet extends HttpServlet {
    /**
     * Handles GET requests by forwarding to the POST method to process the users pending validation.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }
    /**
     * Handles POST requests to retrieve and display the list of users pending validation.
     * The list is retrieved from the database using the UserToValidateDAO and passed to the userToValidateManager.jsp page.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        List<Userstovalidate> userstovalidateList = UserToValidateDAO.getAllUserstovalidate();
        // Set the users list as a request attribute
        request.setAttribute("usersToValidate", userstovalidateList);
        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/userToValidateManager.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
