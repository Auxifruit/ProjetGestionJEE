package com.example.projetjee.controller;

import com.example.projetjee.model.dao.CourseDAO;
import com.example.projetjee.model.dao.LessonDAO;
import com.example.projetjee.model.dao.TeacherDAO;
import com.example.projetjee.model.entities.Cours;
import com.example.projetjee.model.entities.Enseignant;
import com.example.projetjee.model.entities.Seance;
import com.example.projetjee.util.DateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "lessonDeletionServlet", value = "/lessonDeletion-servlet")
public class LessonDeletionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Seance> lessonList = LessonDAO.getAllLessons();

        request.setAttribute("lessons", lessonList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/lessonDeletion.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lessonIdString = request.getParameter("lessonId");

        if(lessonIdString == null || lessonIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une séance.");
            doGet(request, response);
            return;
        }

        int lessonId = Integer.parseInt(lessonIdString);

        if(LessonDAO.isLessonInTable(lessonId) == false) {
            request.setAttribute("erreur", "Erreur : La séance n'existe pas.");
            doGet(request, response);
            return;
        }

        if(LessonDAO.deleteLessonFromTable(lessonId) == true) {
            doGet(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la suppression de la séance.");
            doGet(request, response);
        }
    }


}
