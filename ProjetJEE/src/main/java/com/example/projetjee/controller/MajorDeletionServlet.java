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
 * Servlet responsible for handling the deletion of a major.
 * This servlet processes the deletion of a major by its ID and forwards the request
 * to the major manager page (majorManager-servlet) after performing the deletion operation.
 */
@WebServlet(name = "majorDeletionServlet", value = "/majorDeletion-servlet")
public class MajorDeletionServlet extends HttpServlet {
    /**
     * Handles the GET request. This method redirects to the POST request handler to process the major deletion.
     *
     * @param request The HttpServletRequest containing the request data.
     * @param response The HttpServletResponse to send the response.
     * @throws ServletException If a servlet-related error occurs.
     * @throws IOException If an input/output error occurs.
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the POST request. This method processes the deletion of a major.
     * It retrieves the major ID, checks for its validity, and either deletes the major
     * from the database or returns an error if the major does not exist or the deletion fails.
     *
     * @param request The HttpServletRequest containing the form data.
     * @param response The HttpServletResponse to send the response.
     * @throws ServletException If a servlet-related error occurs.
     * @throws IOException If an input/output error occurs.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String majorIdString = request.getParameter("majorId");

        if(majorIdString == null || majorIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une filière.");
            request.getRequestDispatcher("majorManager-servlet").forward(request, response);
            return;
        }

        int majorId = Integer.parseInt(majorIdString);
        Major major = MajorDAO.getMajorById(majorId);

        if(major == null) {
            request.setAttribute("erreur", "Erreur : La filière n'existe pas.");
            request.getRequestDispatcher("majorManager-servlet").forward(request, response);
            return;
        }

        if(MajorDAO.deleteMajorFromTable(majorId)) {
            request.getRequestDispatcher("majorManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la suppression de la filière.");
            request.getRequestDispatcher("majorManager-servlet").forward(request, response);
        }

    }
}
