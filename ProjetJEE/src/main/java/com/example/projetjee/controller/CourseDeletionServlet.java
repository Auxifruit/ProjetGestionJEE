package com.example.projetjee.controller;

import com.example.projetjee.model.dao.CourseDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "courseDeletionServlet", value = "/courseDeletion-servlet")
public class CourseDeletionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

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
