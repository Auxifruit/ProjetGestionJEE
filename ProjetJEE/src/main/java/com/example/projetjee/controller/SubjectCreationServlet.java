package com.example.projetjee.controller;

import com.example.projetjee.model.dao.SubjectDAO;
import com.example.projetjee.model.entities.Subjects;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
/**
 * Servlet for creating a new subject.
 * It retrieves the list of existing subjects, allows the creation of a new subject,
 * and redirects to the subject management page upon success.
 */
@WebServlet(name = "subjectCreationServlet", value = "/subjectCreation-servlet")
public class SubjectCreationServlet extends HttpServlet {
    /**
     * Handles GET requests to display the subject creation page.
     * It retrieves the list of existing subjects and passes it to the view.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     * @throws IOException if an input or output error occurs
     * @throws ServletException if an error occurs while processing the request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Subjects> subjectList = SubjectDAO.getAllSubjects();
        // Pass the list of subjects to the JSP for display
        request.setAttribute("subjects", subjectList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/subjectCreation.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles POST requests to create a new subject.
     * It retrieves the name of the new subject and adds it to the database.
     * If successful, the user is redirected to the subject management page.
     * In case of an error, an error message is displayed, and the user is redirected to the creation page.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     * @throws ServletException if an error occurs while processing the request
     * @throws IOException if an input or output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectName = request.getParameter("newSubject");

        // Check if the subject name is valid
        if(subjectName == null || subjectName.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez saisir le nom de la nouvelle matière.");
            doGet(request, response);
            return;
        }
        // Create a new Subject object with the subject name
        Subjects subject = new Subjects();
        subject.setSubjectName(subjectName);

        // Attempt to add the subject to the database
        String error = SubjectDAO.addSubjectInTable(subject);
        if(error == null) {
            request.getRequestDispatcher("subjectManager-servlet").forward(request, response);
        }
        else {
            // In case of an error, display an error message and redirect to the creation page
            request.setAttribute("erreur", error);
            doGet(request, response);
        }

    }


}
