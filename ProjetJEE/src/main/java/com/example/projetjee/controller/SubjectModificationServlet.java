package com.example.projetjee.controller;

import com.example.projetjee.model.dao.SubjectDAO;
import com.example.projetjee.model.entities.Subjects;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
/**
 * Servlet that handles the modification of a subject.
 * This servlet allows for the modification of a subject's name.
 * It checks the validity of the provided subject ID and new name,
 * and updates the subject in the database if valid.
 */
@WebServlet(name = "subjectModificationServlet", value = "/subjectModification-servlet")
public class SubjectModificationServlet extends HttpServlet {
    /**
     * Handles GET requests to display the current information of the subject to be modified.
     * It retrieves the subject ID from the request, fetches the subject details from the database,
     * and forwards the request to the subject modification page (subjectModification.jsp).
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     * @throws ServletException if an error occurs during the request processing
     * @throws IOException if an input or output error occurs during the request processing
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectIdString = request.getParameter("subjectId");

        if(subjectIdString == null || subjectIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une matière.");
            request.getRequestDispatcher("subjectManager-servlet").forward(request, response);
            return;
        }

        int subjectId = Integer.parseInt(subjectIdString);
        Subjects subject = SubjectDAO.getSubjectById(subjectId);

        try {
            request.setAttribute("subject", subject);
            request.getRequestDispatcher("WEB-INF/jsp/pages/subjectModification.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Handles POST requests to modify the subject's name.
     * It validates the provided subject ID and new name, and updates the subject in the database.
     * If any validation fails, an error message is set as an attribute and the user is redirected to the modification page.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     * @throws ServletException if an error occurs during the request processing
     * @throws IOException if an input or output error occurs during the request processing
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectIdString = request.getParameter("subjectId");
        String subjectNewName = request.getParameter("subjectNewName");

        if(subjectIdString == null || subjectIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une matière.");
            doGet(request, response);
            return;
        }

        if(subjectNewName == null || subjectNewName.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un nouveau nom.");
            doGet(request, response);
            return;
        }

        int subjectId = Integer.parseInt(subjectIdString);
        // Check if subject exists
        if(SubjectDAO.getSubjectById(subjectId) == null) {
            request.setAttribute("erreur", "Erreur : La matière n'existe pas.");
            doGet(request, response);
            return;
        }

        Subjects subject = SubjectDAO.getSubjectById(subjectId);
        // Check if the new name is the same as the old one
        if(subject.getSubjectName().equals(subjectNewName)) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un nouveau différent.");
            doGet(request, response);
            return;
        }

        subject.setSubjectName(subjectNewName);

        String error = SubjectDAO.modifySubjectFromTable(subject);
        if(error == null) {
            request.getRequestDispatcher("subjectManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", error);
            doGet(request, response);
        }

    }


}
