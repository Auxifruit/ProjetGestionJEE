package com.example.projetjee.controller;

import com.example.projetjee.model.dao.CourseDAO;
import com.example.projetjee.model.dao.SubjectDAO;
import com.example.projetjee.model.entities.Cours;
import com.example.projetjee.model.entities.Matiere;
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
        List<Cours> courseList = CourseDAO.getAllCourses();
        List<Matiere> subjectList = SubjectDAO.getAllSubject();

        request.setAttribute("courses", courseList);
        request.setAttribute("subjects", subjectList);

        try {
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

        if(newCourseName == null || newCourseName.isEmpty()) {
            newCourseName = CourseDAO.getCourseName(courseId);
        }

        int newCourseSubjectId;

        if(newCourseSubjectIdString == null || newCourseSubjectIdString.isEmpty()) {
            newCourseSubjectId = CourseDAO.getCourseSubjectId(courseId);
        }
        else {
            newCourseSubjectId = Integer.parseInt(newCourseSubjectIdString);
        }

        System.out.println("Course id : " + courseId);
        System.out.println("New course name : " + newCourseName);
        System.out.println("New course subject id : " + newCourseSubjectId);


        if(CourseDAO.isCourseInTable(newCourseName, newCourseSubjectId) == true) {
            request.setAttribute("erreur", "Erreur : Le cours existe déjà.");
            doGet(request, response);
            return;
        }

        if(CourseDAO.modifyCourseInTable(courseId, newCourseName, newCourseSubjectId) == true) {
            doGet(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la modification du cours.");
            doGet(request, response);
        }

    }


}
