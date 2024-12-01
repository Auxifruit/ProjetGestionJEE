package com.example.projetjee.controller;

import com.example.projetjee.model.dao.MajorDAO;
import com.example.projetjee.model.entities.Major;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet responsible for handling the creation of new majors.
 * This servlet retrieves a list of existing majors and allows users to create a new major.
 */
@WebServlet(name = "majorCreationServlet", value = "/majorCreation-servlet")
public class MajorCreationServlet extends HttpServlet {
    /**
     * Handles the GET request. This method retrieves all existing majors and forwards the request to
     * the major creation page (majorCreation.jsp).
     *
     * @param request The HttpServletRequest containing the request data.
     * @param response The HttpServletResponse to send the response.
     * @throws IOException If an input/output error occurs.
     * @throws ServletException If a servlet-related error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Major> majorList = MajorDAO.getAllMajor();

        request.setAttribute("majors", majorList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/majorCreation.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the POST request. This method processes the creation of a new major.
     * It retrieves the new major's name, validates the input, and either adds the major to the database
     * or returns an error if the input is invalid.
     *
     * @param request The HttpServletRequest containing the form data.
     * @param response The HttpServletResponse to send the response.
     * @throws ServletException If a servlet-related error occurs.
     * @throws IOException If an input/output error occurs.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String majorName = request.getParameter("newMajor");

        if(majorName == null || majorName.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez saisir le nom de la nouvelle filière.");
            doGet(request, response);
            return;
        }

        Major major = new Major();
        major.setMajorName(majorName);

        String error = MajorDAO.addMajorInTable(major);
        if(error == null) {
            request.getRequestDispatcher("majorManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", error);
            doGet(request, response);
        }

    }


}
