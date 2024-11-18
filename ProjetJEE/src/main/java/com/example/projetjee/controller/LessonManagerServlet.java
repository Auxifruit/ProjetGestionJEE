package com.example.projetjee.controller;

import com.example.projetjee.model.dao.LessonDAO;
import com.example.projetjee.model.entities.Lesson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@WebServlet(name = "lessonManagerServlet", value = "/lessonManager-servlet")
public class LessonManagerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Lesson> lessonList = LessonDAO.getAllLessons();

        request.setAttribute("lessons", lessonList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/lessonManager.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

}
