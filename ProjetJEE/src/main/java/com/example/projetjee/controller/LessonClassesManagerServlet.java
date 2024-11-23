package com.example.projetjee.controller;

import com.example.projetjee.model.dao.ClasseDAO;
import com.example.projetjee.model.dao.LessonDAO;
import com.example.projetjee.model.entities.Classes;
import com.example.projetjee.model.entities.Lesson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "lessonClassesManagerServlet", value = "/lessonClassesManager-servlet")
public class LessonClassesManagerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String lessonIdString = request.getParameter("lessonId");
        List<Classes> availableClassesList;

        if(lessonIdString == null || lessonIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une séance.");
            request.getRequestDispatcher("lessonManager-servlet").forward(request, response);
            return;
        }

        int lessonId = Integer.parseInt(lessonIdString);

        Lesson lesson = LessonDAO.getLessonById(lessonId);
        availableClassesList = ClasseDAO.getAvailableClassesForLesson(lesson.getLessonId());

        request.setAttribute("lesson", lesson);
        request.setAttribute("availableClasses", availableClassesList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/lessonClassesManager.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        doGet(request, response);
    }
}