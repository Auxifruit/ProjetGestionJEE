package com.example.projetjee.controller;

import com.example.projetjee.model.dao.LessonClasseDAO;
import com.example.projetjee.model.dao.LessonDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "lessonClassesAssignationServlet", value = "/lessonClassesAssignation-servlet")
public class LessonClassesAssignationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String classeIdString = request.getParameter("classId");
        String lessonIdString = request.getParameter("lessonId");

        int lessonId = Integer.parseInt(lessonIdString);
        request.setAttribute("lessonId", lessonId);

        if(classeIdString == null || classeIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une classe.");
            request.getRequestDispatcher("lessonClassesManager-servlet").forward(request, response);
            return;
        }

        int classeId = Integer.parseInt(classeIdString);

        if(LessonClasseDAO.canClassParticipate(classeId, lessonId) == false) {
            request.setAttribute("erreur", "Erreur : La classe a une séance à ces horaires.");
            request.getRequestDispatcher("lessonClassesManager-servlet").forward(request, response);
            return;
        }

        if(LessonClasseDAO.addLessonClassInTable(lessonId, classeId) == true) {
            request.getRequestDispatcher("lessonClassesManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de l'assignation de la classe.");
            request.getRequestDispatcher("lessonClassesManager-servlet").forward(request, response);
        }
    }
}

