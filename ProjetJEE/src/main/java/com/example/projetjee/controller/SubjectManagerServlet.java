package com.example.projetjee.controller;

import com.example.projetjee.model.dao.SubjectDAO;
import com.example.projetjee.model.entities.Subjects;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * Servlet that handles the management of subjects.
 * This servlet is responsible for retrieving the list of all subjects from the database
 * and forwarding it to the subject management page (subjectManager.jsp).
 */
@WebServlet(name = "subjectManagerServlet", value = "/subjectManager-servlet")
public class SubjectManagerServlet extends HttpServlet {
    /**
     * Handles GET requests to retrieve the list of all subjects.
     * It fetches the list of subjects from the database and forwards it to the JSP page
     * for displaying the subjects to the user.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Subjects> subjectList = SubjectDAO.getAllSubjects();
        // Set the list of subjects as a request attribute
        request.setAttribute("subjects", subjectList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/subjectManager.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Handles POST requests by forwarding them to the doGet method.
     * This is typically used for situations where both GET and POST requests
     * trigger the same functionality, such as displaying the list of subjects.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

}
