package com.example.projetjee.controller;

import com.example.projetjee.model.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/cydatabase";
    private static final String DB_USERNAME = "root"; // Change en fonction de ton utilisateur MySQL
    private static final String DB_PASSWORD = ""; // Ton mot de passe MySQL

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirige vers la page de connexion
        request.getRequestDispatcher("WEB-INF/jsp/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if(email == null || email.isEmpty()) {
            request.setAttribute("error", "Erreur : Veuillez saisir l'email.");
            request.getRequestDispatcher("WEB-INF/jsp/pages/login.jsp").forward(request, response);
            return;
        }

        if(password == null || password.isEmpty()) {
            request.setAttribute("error", "Erreur : Veuillez saisir un mot de passe.");
            request.getRequestDispatcher("WEB-INF/jsp/pages/login.jsp").forward(request, response);
            return;
        }

        Integer userId = UserDAO.userConnection(email, password);

        if(userId == null) {
            request.setAttribute("error", "Erreur : L'email ou le mot de passe est incorrecte");
            request.getRequestDispatcher("WEB-INF/jsp/pages/login.jsp").forward(request, response);
            return;
        }

        try {
            request.getSession().setAttribute("user", userId.intValue());
            request.getRequestDispatcher("WEB-INF/jsp/pages/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
