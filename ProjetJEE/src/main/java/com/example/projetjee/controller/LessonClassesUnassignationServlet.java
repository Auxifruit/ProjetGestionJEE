package com.example.projetjee.controller;

import com.example.projetjee.model.dao.ClasseDAO;
import com.example.projetjee.model.dao.LessonClasseDAO;
import com.example.projetjee.model.dao.LessonDAO;
import com.example.projetjee.model.entities.Classe;
import com.example.projetjee.model.entities.Seance;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "lessonClassesUnassignationServlet", value = "/lessonClassesUnassignation-servlet")
public class LessonClassesUnassignationServlet extends HttpServlet {

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


        if(LessonClasseDAO.deleteLessonClassInTable(lessonId, classeId) == true) {
            request.getRequestDispatcher("lessonClassesManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la désaffectation de la classe.");
            request.getRequestDispatcher("lessonClassesManager-servlet").forward(request, response);
        }
    }
}

