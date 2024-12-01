package com.example.projetjee.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Servlet responsible for handling user logout.
 * This servlet invalidates the current session and redirects the user to the homepage.
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles the GET request. This method invalidates the user session and redirects to the homepage.
     *
     * @param request  The HttpServletRequest object containing the request information.
     * @param response The HttpServletResponse object used to send a response.
     * @throws ServletException If an error occurs during the processing of the request.
     * @throws IOException      If an input/output error occurs.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();

        response.sendRedirect("index.jsp");
    }
}