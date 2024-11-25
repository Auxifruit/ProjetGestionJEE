package com.example.projetjee.controller;

import com.example.projetjee.model.dao.GradeDAO;
import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.entities.Grade;
import com.example.projetjee.model.entities.Role;
import com.example.projetjee.model.entities.Users;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@WebServlet(name = "gradeManagerServlet", value = "/gradeManager-servlet")
public class GradeManagerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Grade> gradeList = GradeDAO.getAllGrade();

        request.setAttribute("grades", gradeList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/gradeManager.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

}
