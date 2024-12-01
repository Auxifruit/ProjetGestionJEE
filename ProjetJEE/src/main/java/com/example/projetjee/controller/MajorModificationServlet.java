package com.example.projetjee.controller;

import com.example.projetjee.model.dao.MajorDAO;
import com.example.projetjee.model.entities.Major;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet responsible for managing the modification of majors.
 * This servlet allows the modification of the name of an existing major.
 */
@WebServlet(name = "majorModificationServlet", value = "/majorModification-servlet")
public class MajorModificationServlet extends HttpServlet {
    /**
     * Handles the GET request. This method retrieves the major by its ID and forwards the request
     * to the major modification page where the user can modify the major's name.
     *
     * @param request The HttpServletRequest containing the request data.
     * @param response The HttpServletResponse to send the response.
     * @throws ServletException If an error occurs during request processing.
     * @throws IOException If an input/output error occurs during request/response handling.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String majorIdString = request.getParameter("majorId");

        if(majorIdString == null || majorIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une filière.");
            request.getRequestDispatcher("majorManager-servlet").forward(request, response);
            return;
        }

        int majorIdId = Integer.parseInt(majorIdString);
        Major major = MajorDAO.getMajorById(majorIdId);

        try {
            request.setAttribute("major", major);
            request.getRequestDispatcher("WEB-INF/jsp/pages/majorModification.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the POST request. This method processes the submitted form to modify a major's name.
     * It checks if the provided major ID and new name are valid, then modifies the major in the database.
     *
     * @param request The HttpServletRequest containing the form data.
     * @param response The HttpServletResponse to send the response.
     * @throws ServletException If an error occurs during request processing.
     * @throws IOException If an input/output error occurs during request/response handling.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String majorIdString = request.getParameter("majorId");
        String majorNewName = request.getParameter("majorNewName");

        // Check if the major ID is missing or empty. If so, send an error message and call doGet to re-display the modification page.
        if(majorIdString == null || majorIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une filière.");
            doGet(request, response);
            return;
        }

        // Check if the new major name is missing or empty. If so, send an error message and call doGet to re-display the modification page.
        if(majorNewName == null || majorNewName.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un nouveau nom.");
            doGet(request, response);
            return;
        }

        int majorId = Integer.parseInt(majorIdString);
        Major major = MajorDAO.getMajorById(majorId);

        if(major == null) {
            request.setAttribute("erreur", "Erreur : La filière n'existe pas.");
            doGet(request, response);
            return;
        }

        if(major.getMajorName().equals(majorNewName)) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un nom différent.");
            doGet(request, response);
            return;
        }

        major.setMajorName(majorNewName);

        // Attempt to modify the major in the database. If successful, proceed with forwarding to the major manager page.
        String error = MajorDAO.modifyMajorFromTable(major);
        if(error == null) {
            request.getRequestDispatcher("majorManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", error);
            doGet(request, response);
        }

    }


}
