package com.example.projetjee.controller;

import com.example.projetjee.model.dao.LessonDAO;
import com.example.projetjee.model.entities.Lesson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
/**
 * Servlet implementation class LessonManagerServlet.
 * This servlet is responsible for managing the list of lessons.
 * It retrieves the lessons from the database and displays them on the lesson manager page.
 */
@WebServlet(name = "lessonManagerServlet", value = "/lessonManager-servlet")
public class LessonManagerServlet extends HttpServlet {
    /**
     * Handles the HTTP GET request to display the list of lessons.
     * It fetches all lessons from the database and forwards the request to the lesson manager page.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Lesson> lessonList = LessonDAO.getAllLesson();

        request.setAttribute("lessons", lessonList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/lessonManager.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Handles the HTTP POST request by calling the doGet method.
     * This method is used when the client submits a POST request for lesson management.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

}
