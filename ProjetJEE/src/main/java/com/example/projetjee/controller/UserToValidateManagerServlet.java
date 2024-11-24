package com.example.projetjee.controller;

import com.example.projetjee.model.dao.UserToValidateDAO;
import com.example.projetjee.model.entities.Userstovalidate;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@WebServlet(name = "userToValidateManagerServlet", value = "/userToValidateManager-servlet")
public class UserToValidateManagerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        List<Userstovalidate> userstovalidateList = UserToValidateDAO.getAllUserstovalidate();

        request.setAttribute("usersToValidate", userstovalidateList);
        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/userToValidateManager.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
