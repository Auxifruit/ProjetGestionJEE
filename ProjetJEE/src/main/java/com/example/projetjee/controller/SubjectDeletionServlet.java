package com.example.projetjee.controller;

import com.example.projetjee.model.dao.SubjectDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet for deleting a subject.
 * It handles the request to delete a specific subject from the database.
 * It checks if the subject exists and if the deletion was successful, and redirects to the subject management page.
 */
@WebServlet(name = "subjectDeletionServlet", value = "/subjectDeletion-servlet")
public class SubjectDeletionServlet extends HttpServlet {
    /**
     * Handles GET requests by forwarding them to the POST method.
     * This method is used for situations where both GET and POST requests
     * should trigger the same logic, like subject deletion in this case.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     * @throws ServletException if an error occurs while processing the request
     * @throws IOException if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles POST requests to delete a subject from the database.
     * It checks if the subject exists, then attempts to delete it.
     * If the deletion is successful, it redirects to the subject management page.
     * If an error occurs, it displays an error message and redirects back to the subject manager.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     * @throws ServletException if an error occurs while processing the request
     * @throws IOException if an input or output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectIdString = request.getParameter("subjectId");

        if(subjectIdString == null || subjectIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une matière.");
            request.getRequestDispatcher("subjectManager-servlet").forward(request, response);
            return;
        }
        // Convert the subject ID to an integer
        int subjectId = Integer.parseInt(subjectIdString);
        // Check if the subject exists in the database
        if(SubjectDAO.getSubjectById(subjectId) == null) {
            request.setAttribute("erreur", "Erreur : La matière n'existe pas.");
            request.getRequestDispatcher("subjectManager-servlet").forward(request, response);
            return;
        }

        // Attempt to delete the subject from the database
        if(SubjectDAO.deleteSubjectFromTable(subjectId) == true) {
            request.getRequestDispatcher("subjectManager-servlet").forward(request, response);
        }
        else {
            // If deletion failed, display an error message
            request.setAttribute("erreur", "Erreur : Erreur lors de la suppression de la matière.");
            request.getRequestDispatcher("subjectManager-servlet").forward(request, response);
        }

    }
}
