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

/**
 * Servlet implementation class GradeManagerServlet.
 * This servlet handles the management and display of grades.
 * It retrieves all grades from the database and forwards the request to the grade manager page.
 */
@WebServlet(name = "gradeManagerServlet", value = "/gradeManager-servlet")
public class GradeManagerServlet extends HttpServlet {
    /**
     * Handles the GET request to retrieve and display all grades.
     * This method retrieves all grades from the database using the GradeDAO,
     * sets them as an attribute, and forwards the request to the grade manager page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     */
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

    /**
     * Handles the POST request by delegating the processing to the GET method.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

}
