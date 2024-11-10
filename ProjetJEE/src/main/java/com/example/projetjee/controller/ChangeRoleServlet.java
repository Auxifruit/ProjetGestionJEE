package com.example.projetjee.controller;

import com.example.projetjee.model.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "changeRoleServlet", value = "/changeRole-servlet")
public class ChangeRoleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int newRoleID = Integer.parseInt(request.getParameter("newRoleID"));
        String userParam = request.getParameter("user");

        if (userParam == null || userParam.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un utilisateur.");
            this.getServletContext().getRequestDispatcher("/user-servlet").forward(request,response);
        }

        int userId = Integer.parseInt(userParam);

        if (newRoleID == UserDAO.getRoleIdByUserID(userId)) {
            request.setAttribute("erreur", "Erreur : L'utilisateur a déjà ce rôle.");
            this.getServletContext().getRequestDispatcher("/user-servlet").forward(request,response);
        }

        if(UserDAO.modifyUserRole(userId, UserDAO.getRoleIdByUserID(userId), newRoleID) == true) {
            this.getServletContext().getRequestDispatcher("/user-servlet").forward(request,response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Une erreur est survenue lors de la modification du rôle.");
            this.getServletContext().getRequestDispatcher("/user-servlet").forward(request,response);
        }
    }
}
