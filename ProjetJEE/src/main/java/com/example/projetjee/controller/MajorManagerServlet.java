package com.example.projetjee.controller;

import com.example.projetjee.model.dao.MajorDAO;
import com.example.projetjee.model.entities.Major;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * Servlet responsible for managing majors.
 * This servlet retrieves all available majors from the database and forwards the request to the major management page.
 */
@WebServlet(name = "majorManagerServlet", value = "/majorManager-servlet")
public class MajorManagerServlet extends HttpServlet {
    /**
     * Handles the GET request. This method retrieves all majors from the database
     * and forwards the request to the major management page (majorManager.jsp).
     *
     * @param request The HttpServletRequest containing the request data.
     * @param response The HttpServletResponse to send the response.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Major> majorList = MajorDAO.getAllMajor();

        request.setAttribute("majors", majorList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/majorManager.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Handles the POST request. This method simply calls the doGet method to retrieve and display the majors.
     *
     * @param request The HttpServletRequest containing the form data.
     * @param response The HttpServletResponse to send the response.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

}
