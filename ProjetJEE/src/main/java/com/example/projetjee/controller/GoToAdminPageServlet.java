package com.example.projetjee.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet that redirects the user to the admin page.
 * This is typically used for navigating to an administrative interface.
 */
@WebServlet(name = "goToAdminPageServlet", value = "/goToAdminPage-servlet")
public class GoToAdminPageServlet extends HttpServlet {
    /**
     * Handles the GET request to forward to the admin page.
     * This method delegates to the POST method.
     *
     * @param request the HttpServletRequest containing the request data
     * @param response the HttpServletResponse to send the response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }

    /**
     * Handles the POST request to forward to the admin page.
     * This method forwards the request to the "adminPage.jsp".
     *
     * @param request the HttpServletRequest containing the request data
     * @param response the HttpServletResponse to send the response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/adminPage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
