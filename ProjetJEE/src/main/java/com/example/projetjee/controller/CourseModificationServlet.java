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
 * Servlet implementation for managing course modifications.
 * This servlet handles both displaying the course modification page
 * and processing the form submission to update course information.
 */
@WebServlet(name = "courseModificationServlet", value = "/courseModification-servlet")
public class CourseModificationServlet extends HttpServlet {
    /**
     * Handles GET requests to display the course modification page.
     * This includes retrieving the course details and available subjects
     * to populate the form fields.
     *
     * @param request  the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws IOException if an input or output error occurs
     * @throws ServletException if the request cannot be handled
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String courseIdString = request.getParameter("courseId");
        List<Subjects> subjectList = SubjectDAO.getAllSubjects();

        // If no courseId is provided, redirect to the course manager with an error message
        if(courseIdString == null || courseIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un cours.");
            request.getRequestDispatcher("courseManager-servlet").forward(request, response);
            return;
        }

        int courseId = Integer.parseInt(courseIdString);
        Course course = CourseDAO.getCourseById(courseId);

        try {
            request.setAttribute("course", course);
            request.setAttribute("subjects", subjectList);
            request.getRequestDispatcher("WEB-INF/jsp/pages/courseModification.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles POST requests to process the form submission for modifying a course.
     * This method updates the course information based on the provided input.
     *
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException if the request cannot be handled
     * @throws IOException if an input or output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseIdString = request.getParameter("courseId");
        String newCourseSubjectIdString = request.getParameter("newCourseSubjectId");
        String newCourseName = request.getParameter("newCourseName");

        if(courseIdString == null || courseIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un cours.");
            doGet(request, response);
            return;
        }

        // If neither the course name nor subject id is provided for modification, show an error
        if((newCourseSubjectIdString == null || newCourseSubjectIdString.isEmpty()) && (newCourseName == null || newCourseName.isEmpty())) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir au moins un champs à modifier.");
            doGet(request, response);
            return;
        }

        int courseId = Integer.parseInt(courseIdString);
        Course course = CourseDAO.getCourseById(courseId);

        if(newCourseName == null || newCourseName.isEmpty()) {
            newCourseName = course.getCourseName();
        }

        // If the new course subject is empty, retain the old subject
        int newCourseSubjectId;

        if(newCourseSubjectIdString == null || newCourseSubjectIdString.isEmpty()) {
            newCourseSubjectId = course.getSubjectId();
        }
        else {
            newCourseSubjectId = Integer.parseInt(newCourseSubjectIdString);
        }

        course.setCourseName(newCourseName);
        course.setSubjectId(newCourseSubjectId);

        String error = CourseDAO.modifyCourseFromTable(course);
        if(error == null) {
            request.getRequestDispatcher("courseManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", error);
            doGet(request, response);
        }

    }


}
