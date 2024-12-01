package com.example.projetjee.controller;

import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.util.HashPswdUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet responsible for handling user login.
 * This servlet allows a user to log in by validating their email and password.
 * It redirects to the homepage if the login is successful or returns an error if the credentials are incorrect.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    /**
     * Handles the GET request. This method is used to display the login page.
     * It simply redirects the user to the JSP login page.
     *
     * @param request  The HttpServletRequest object containing the request information.
     * @param response The HttpServletResponse object used to send a response.
     * @throws ServletException If an error occurs during the processing of the request.
     * @throws IOException      If an input/output error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/jsp/pages/login.jsp").forward(request, response);
    }

    /**
     * Handles the POST request. This method processes the login form submitted by the user.
     * It validates the email and password, and either redirects to the homepage or shows an error.
     *
     * @param request  The HttpServletRequest object containing the request information.
     * @param response The HttpServletResponse object used to send a response.
     * @throws ServletException If an error occurs during the processing of the request.
     * @throws IOException      If an input/output error occurs.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if(email == null || email.isEmpty()) {
            request.setAttribute("error", "Erreur : Veuillez saisir un email.");
            request.getRequestDispatcher("WEB-INF/jsp/pages/login.jsp").forward(request, response);
            return;
        }

        if(password == null || password.isEmpty()) {
            request.setAttribute("error", "Erreur : Veuillez saisir un mot de passe.");
            request.getRequestDispatcher("WEB-INF/jsp/pages/login.jsp").forward(request, response);
            return;
        }

        Integer userId = UserDAO.userConnection(email, HashPswdUtil.hashPassword(password));

        if(userId == null) {
            request.setAttribute("error", "Erreur : L'email ou le mot de passe est incorrect.");
            request.getRequestDispatcher("WEB-INF/jsp/pages/login.jsp").forward(request, response);
            return;
        }

        try {
            request.getSession().setAttribute("user", userId.intValue());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
