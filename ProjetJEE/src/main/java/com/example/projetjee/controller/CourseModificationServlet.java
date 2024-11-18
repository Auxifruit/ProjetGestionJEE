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

@WebServlet(name = "courseModificationServlet", value = "/courseModification-servlet")
public class CourseModificationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String courseIdString = request.getParameter("courseId");
        List<Subjects> subjectList = SubjectDAO.getAllSubject();

        if(courseIdString == null || courseIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un cours.");
            request.getRequestDispatcher("courseManager-servlet").forward(request, response);
            return;
        }

        int courseId = Integer.parseInt(courseIdString);
        Course course = CourseDAO.getCourses(courseId);

        try {
            request.setAttribute("course", course);
            request.setAttribute("subjects", subjectList);
            request.getRequestDispatcher("WEB-INF/jsp/pages/courseModification.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

        if((newCourseSubjectIdString == null || newCourseSubjectIdString.isEmpty()) && (newCourseName == null || newCourseName.isEmpty())) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir au moins un champs à modifier.");
            doGet(request, response);
            return;
        }

        int courseId = Integer.parseInt(courseIdString);
        Course course = CourseDAO.getCourses(courseId);

        if(newCourseName == null || newCourseName.isEmpty()) {
            newCourseName = course.getCourseName();
        }

        int newCourseSubjectId;

        if(newCourseSubjectIdString == null || newCourseSubjectIdString.isEmpty()) {
            newCourseSubjectId = course.getSubjectId();
        }
        else {
            newCourseSubjectId = Integer.parseInt(newCourseSubjectIdString);
        }

        if(CourseDAO.isCourseInTable(newCourseName, newCourseSubjectId) == true) {
            request.setAttribute("erreur", "Erreur : Le cours existe déjà.");
            doGet(request, response);
            return;
        }

        if(CourseDAO.modifyCourseInTable(courseId, newCourseName, newCourseSubjectId) == true) {
            request.getRequestDispatcher("courseManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la modification du cours.");
            doGet(request, response);
        }

    }


}
