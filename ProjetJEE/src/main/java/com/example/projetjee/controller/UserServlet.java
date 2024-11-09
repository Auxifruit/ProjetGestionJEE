package com.example.projetjee.controller;

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
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String roleFilter = request.getParameter("roleFilter");
        List<Utilisateur> usersList = userDAO.getAllUsers(roleFilter);

        request.setAttribute("users", usersList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/roleList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }
}
