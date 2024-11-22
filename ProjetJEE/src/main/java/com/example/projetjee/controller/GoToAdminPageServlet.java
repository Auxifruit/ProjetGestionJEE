package com.example.projetjee.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "goToAdminPageServlet", value = "/goToAdminPage-servlet")
public class GoToAdminPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/adminPage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
