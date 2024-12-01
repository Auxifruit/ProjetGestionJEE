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
/**
 * Servlet implementation class LessonClassesManagerServlet.
 * This servlet manages the classes available for a specific lesson and forwards the data to the corresponding JSP page.
 */
@WebServlet(name = "lessonClassesManagerServlet", value = "/lessonClassesManager-servlet")
public class LessonClassesManagerServlet extends HttpServlet {

    /**
     * Handles the HTTP GET request to retrieve and display available classes for a given lesson.
     * It fetches the lesson by its ID and retrieves the list of available classes associated with that lesson.
     * If the lesson ID is not provided or invalid, it redirects to the lesson manager servlet with an error message.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws IOException if an input or output error occurs
     * @throws ServletException if a servlet error occurs
     */
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
    /**
     * Handles the HTTP POST request. This method simply calls the doGet method to reuse the logic for fetching and displaying
     * available classes for a given lesson.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws IOException if an input or output error occurs
     * @throws ServletException if a servlet error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        doGet(request, response);
    }
}