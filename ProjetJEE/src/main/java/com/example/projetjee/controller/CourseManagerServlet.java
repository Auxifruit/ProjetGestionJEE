package com.example.projetjee.controller;

import com.example.projetjee.model.dao.CourseDAO;
import com.example.projetjee.model.entities.Course;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * Servlet responsible for managing courses.
 * This servlet retrieves and displays the list of courses.
 */
@WebServlet(name = "courseManagerServlet", value = "/courseManager-servlet")
public class CourseManagerServlet extends HttpServlet {
    /**
     * Handles GET requests to fetch and display all courses.
     * Forwards the course list to the course manager page.
     *
     * @param request  the HttpServletRequest containing the request data
     * @param response the HttpServletResponse to send the response to the client
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Course> courseList = CourseDAO.getAllCourses();

        request.setAttribute("courses", courseList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/courseManager.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles POST requests, delegating to the GET handler.
     * Since this servlet doesn't do anything special for POST, we delegate to GET.
     *
     * @param request  the HttpServletRequest containing the request data
     * @param response the HttpServletResponse to send the response to the client
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

}
