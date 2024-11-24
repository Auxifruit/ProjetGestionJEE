package com.example.projetjee.controller;

import com.example.projetjee.model.dao.UserToValidateDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "refuseUserServletServlet", value = "/refuseUser-servlet")

public class RefuseUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userToValidateIdString = request.getParameter("userToValidateId");

        if(userToValidateIdString == null || userToValidateIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un utilisateur.");
            request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
            return;
        }

        int userToValidateId = Integer.parseInt(userToValidateIdString);

        if(UserToValidateDAO.deleteUserstovalidateFromTable(userToValidateId) == true) {
            request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
        } else {
            request.setAttribute("erreur", "Erreur : Erreur lors du refus de l'utilisateur.");
            request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
        }
    }
}
