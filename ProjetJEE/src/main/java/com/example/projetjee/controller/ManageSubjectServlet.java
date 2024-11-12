package com.example.projetjee.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ManageSubjectServlet", value = "/manageSubject-servlet")
public class ManageSubjectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/manageSubject.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
