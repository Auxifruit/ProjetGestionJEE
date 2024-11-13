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

@WebServlet(name = "courseDeletionServlet", value = "/courseDeletion-servlet")
public class CourseDeletionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Cours> courseList = CourseDAO.getAllCourses();

        request.setAttribute("courses", courseList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/courseDeletion.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseIdString = request.getParameter("courseId");

        if(courseIdString == null || courseIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un cours.");
            doGet(request, response);
            return;
        }

        int courseId = Integer.parseInt(courseIdString);

        if(CourseDAO.isCourseInTable(courseId) == false) {
            request.setAttribute("erreur", "Erreur : Le cours n'existe déjà.");
            doGet(request, response);
            return;
        }

        if(CourseDAO.deleteCourseFromTable(courseId) == true) {
            doGet(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la suppression du cours.");
            doGet(request, response);
        }

    }


}
