package com.example.projetjee.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Vérifie si l'utilisateur est connecté
        if (request.getSession().getAttribute("user") == null) {
            // Redirige vers la page de connexion si pas connecté
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Redirige vers la page JSP dashboard
        request.getRequestDispatcher("/WEB-INF/jsp/pages/dashboard.jsp").forward(request, response);
    }
}
