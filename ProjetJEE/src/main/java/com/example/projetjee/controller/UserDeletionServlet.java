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

@WebServlet(name = "userDeletionServlet", value = "/userDeletion-servlet")
public class UserDeletionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

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

        if(connectedUserId.equals(userId)) {
            request.setAttribute("erreur", "Erreur : Vous ne pouvez pas vous supprimer vous-même.");
            request.getRequestDispatcher("userManager-servlet?roleFilter="+role).forward(request, response);
            return;
        }

        if(user == null) {
            request.setAttribute("erreur", "Erreur : L'utilisateur n'existe pas.");
            request.getRequestDispatcher("userManager-servlet?roleFilter="+role).forward(request, response);
            return;
        }

        if(UserDAO.deleteUserFromTable(userId) == true) {
            request.getRequestDispatcher("userManager-servlet?roleFilter="+role).forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la suppression de l'utilisateur.");
            request.getRequestDispatcher("userManager-servlet?roleFilter="+role).forward(request, response);
        }

    }
}
