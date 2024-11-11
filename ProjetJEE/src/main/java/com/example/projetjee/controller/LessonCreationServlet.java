package com.example.projetjee.controller;

import com.example.projetjee.model.dao.LessonDAO;
import com.example.projetjee.util.DateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "lessonCreationServlet", value = "/lessonCreation-servlet")
public class LessonCreationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int courseId = Integer.parseInt(request.getParameter("course"));
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        int teacherId = Integer.parseInt(request.getParameter("teacher"));

        if(startDate == null || startDate.trim().isEmpty() || endDate == null || endDate.trim().isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez saisir les 2 dates nécessaire pour la création d'une séance.");
            this.getServletContext().getRequestDispatcher("/lesson-servlet").forward(request, response);
            return;
        }
        if(DateUtil.areDatesValid(startDate, endDate) == false) {
            request.setAttribute("erreur", "Erreur : Veuillez saisir 2 dates valides.");
            this.getServletContext().getRequestDispatcher("/lesson-servlet").forward(request, response);
            return;
        }
        if(LessonDAO.isLessonPossible(teacherId, startDate, endDate) == false) {
            request.setAttribute("erreur", "Erreur : Le professeur a déjà cours à ces dates.");
            this.getServletContext().getRequestDispatcher("/lesson-servlet").forward(request, response);
            return;
        }

        LessonDAO.AddLesson(null, startDate, endDate, courseId, teacherId);
    }


}
