package com.example.projetjee.controller;

import com.example.projetjee.model.dao.CourseDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet responsible for handling the creation of new courses.
 * This servlet allows the user to create a new course by associating it with a subject.
 */
@WebServlet(name = "courseDeletionServlet", value = "/courseDeletion-servlet")
public class CourseDeletionServlet extends HttpServlet {

    /**
     * Handles GET requests to retrieve the available courses and subjects,
     * and forwards the data to the course creation form.
     *
     * @param request  the HttpServletRequest containing the request data
     * @param response the HttpServletResponse to send the response to the client
     * @throws IOException if an input or output error occurs during the request/response cycle
     * @throws ServletException if an error occurs during servlet processing
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    /**
     * Handles POST requests to process the creation of a new course.
     * It validates the inputs and attempts to add the new course to the database.
     *
     * @param request  the HttpServletRequest containing the request data
     * @param response the HttpServletResponse to send the response to the client
     * @throws ServletException if an error occurs during servlet processing
     * @throws IOException if an input or output error occurs during the request/response cycle
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseIdString = request.getParameter("courseId");

        if(courseIdString == null || courseIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une matière.");
            request.getRequestDispatcher("courseManager-servlet").forward(request, response);
            return;
        }

        int courseId = Integer.parseInt(courseIdString);

        if(CourseDAO.getCourseById(courseId) == null) {
            request.setAttribute("erreur", "Erreur : Le cours n'existe pas.");
            request.getRequestDispatcher("courseManager-servlet").forward(request, response);
            return;
        }

        if(CourseDAO.deleteCourseFromTable(courseId) == true) {
            request.getRequestDispatcher("courseManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la suppression du cours.");
            request.getRequestDispatcher("courseManager-servlet").forward(request, response);
        }

    }
}
