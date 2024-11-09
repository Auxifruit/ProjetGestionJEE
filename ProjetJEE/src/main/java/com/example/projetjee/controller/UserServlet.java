package com.example.projetjee.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.projetjee.model.entities.Utilisateur;
import com.example.projetjee.model.dao.UserDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "userServlet", value = "/user-servlet")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
        String roleFilter = request.getParameter("roleFilter");
        List<Utilisateur> usersList = UserDAO.getAllUsers(roleFilter);

        request.setAttribute("users", usersList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/roleList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
