package com.example.projetjee.controller;

import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.entities.Role;
import com.example.projetjee.model.entities.Users;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@WebServlet(name = "userManagerServlet", value = "/userManager-servlet")
public class UserManagerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String roleFilterString = request.getParameter("roleFilter");
        Role roleFilter = null;
        if(roleFilterString != null && !roleFilterString.isEmpty()) {
            roleFilter = Role.valueOf(roleFilterString);
        }

        List<Users> usersList = UserDAO.getAllUsersByFilter(roleFilter);

        request.setAttribute("users", usersList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/userManager.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

}
