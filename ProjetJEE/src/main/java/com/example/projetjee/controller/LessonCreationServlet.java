package com.example.projetjee.controller;

import com.example.projetjee.model.dao.LessonDAO;
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

        System.out.println(courseId);
        System.out.println(startDate);
        System.out.println(endDate);
        System.out.println(teacherId);

        LessonDAO.AddLesson(1, formatDate(startDate), formatDate(endDate), courseId, teacherId);
    }

    private String formatDate(String date) {
        // Replace the "T" with a space and add seconds "00"
        if (date != null) {
            date = date.replace("T", " ");
        }
        return date;
    }
}
