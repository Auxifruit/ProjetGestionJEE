package com.example.projetjee.controller;

import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.entities.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "changeRoleServlet", value = "/changeRole-servlet")
public class ChangeRoleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String roleFilter = request.getParameter("roleFilter");
        List<Users> usersList = UserDAO.getAllUsers(roleFilter);

        request.setAttribute("users", usersList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/roleList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int newRoleID = Integer.parseInt(request.getParameter("newRoleID"));
        String userParam = request.getParameter("user");

        if (userParam == null || userParam.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un utilisateur.");
            doGet(request, response);
        }

        int userId = Integer.parseInt(userParam);

        if (newRoleID == UserDAO.getRoleIdByUserID(userId)) {
            request.setAttribute("erreur", "Erreur : L'utilisateur a déjà ce rôle.");
            doGet(request, response);
        }

        if(UserDAO.modifyUserRole(userId, UserDAO.getRoleIdByUserID(userId), newRoleID) == true) {
            doGet(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Une erreur est survenue lors de la modification du rôle.");
            doGet(request, response);
        }
    }
}
