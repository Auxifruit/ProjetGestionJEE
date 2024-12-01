package com.example.projetjee.controller;

import com.example.projetjee.model.dao.GradeDAO;
import com.example.projetjee.model.dao.SubjectDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet implementation class GradeDeletionServlet.
 * This servlet handles the deletion of grades from the system.
 * It checks if the grade exists and then proceeds to delete it.
 */
@WebServlet(name = "gradeDeletionServlet", value = "/gradeDeletion-servlet")
public class GradeDeletionServlet extends HttpServlet {

    /**
     * Handles the GET request by delegating the processing to the POST method.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the POST request for grade deletion.
     * This method performs the following steps:
     * 1. Retrieves the grade ID from the request.
     * 2. Verifies that the grade ID is valid and exists.
     * 3. If the grade exists, attempts to delete it from the database.
     * 4. If the deletion is successful, forwards the request to the grade manager page.
     * 5. If any error occurs, sets an error message and forwards the request to the grade manager page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an input or output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String gradeIdString = request.getParameter("gradeId");

        if(gradeIdString == null || gradeIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une note.");
            request.getRequestDispatcher("gradeManager-servlet").forward(request, response);
            return;
        }

        int gradeId = Integer.parseInt(gradeIdString);

        if(GradeDAO.getGradeById(gradeId) == null) {
            request.setAttribute("erreur", "Erreur : La note n'existe pas.");
            request.getRequestDispatcher("gradeManager-servlet").forward(request, response);
            return;
        }

        // Attempt to delete the grade
        if(GradeDAO.deleteGradeFromTable(gradeId) == true) {
            request.getRequestDispatcher("gradeManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la suppression de la note.");
            request.getRequestDispatcher("gradeManager-servlet").forward(request, response);
        }

    }
}
