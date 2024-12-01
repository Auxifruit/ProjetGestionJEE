package com.example.projetjee.controller;

import com.example.projetjee.model.dao.CourseDAO;
import com.example.projetjee.model.dao.SubjectDAO;
import com.example.projetjee.model.entities.Course;
import com.example.projetjee.model.entities.Subjects;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet responsible for handling the creation of new courses.
 * This servlet allows the user to create a new course by associating it with a subject.
 */
@WebServlet(name = "courseCreationServlet", value = "/courseCreation-servlet")
public class CourseCreationServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Course> courseList = CourseDAO.getAllCourses();
        List<Subjects> subjectList = SubjectDAO.getAllSubjects();

        request.setAttribute("courses", courseList);
        request.setAttribute("subjects", subjectList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/courseCreation.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        String subjectIdString = request.getParameter("courseSubjectId");
        String courseName = request.getParameter("courseName");

        // Validate that a subject is selected
        if(subjectIdString == null || subjectIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une matière.");
            doGet(request, response);
            return;
        }
        // Validate that the course name is provided
        if(courseName == null || courseName.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez saisir le nom du nouveau cours.");
            doGet(request, response);
            return;
        }

        int subjectId = Integer.parseInt(subjectIdString);

        // Create a new Course object and set its attributes
        Course course = new Course();
        course.setCourseName(courseName);
        course.setSubjectId(subjectId);

        String error = CourseDAO.addCourseInTable(course);
        if(error == null) {
            request.getRequestDispatcher("courseManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", error);
            doGet(request, response);
        }

    }


}
