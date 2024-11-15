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

@WebServlet(name = "courseCreationServlet", value = "/courseCreation-servlet")
public class CourseCreationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Cours> courseList = CourseDAO.getAllCourses();
        List<Matiere> subjectList = SubjectDAO.getAllSubject();

        request.setAttribute("courses", courseList);
        request.setAttribute("subjects", subjectList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/courseCreation.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectIdString = request.getParameter("courseSubjectId");
        String courseName = request.getParameter("courseName");

        if(subjectIdString == null || subjectIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une matière.");
            doGet(request, response);
            return;
        }

        if(courseName == null || courseName.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez saisir le nom du nouveau cours.");
            doGet(request, response);
            return;
        }

        int subjectId = Integer.parseInt(subjectIdString);

        if(CourseDAO.isCourseInTable(courseName, subjectId) == true) {
            request.setAttribute("erreur", "Erreur : Le cours existe déjà.");
            doGet(request, response);
            return;
        }

        if(CourseDAO.addCourseInTable(courseName, subjectId) == true) {
            request.getRequestDispatcher("courseManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de l'ajout du cours.");
            doGet(request, response);
        }

    }


}
