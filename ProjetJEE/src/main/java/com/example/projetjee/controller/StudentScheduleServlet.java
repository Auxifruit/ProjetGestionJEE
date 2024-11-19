package com.example.projetjee.controller;

import com.example.projetjee.model.dao.LessonDAO;
import com.example.projetjee.model.entities.Lesson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@WebServlet(name = "studentScheduleServlet", value = "/studentSchedule-servlet")
public class StudentScheduleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentIdString = request.getParameter("studentId");

        if(studentIdString == null || studentIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un étudiant.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        int studentId = Integer.parseInt(studentIdString);

        List<Lesson> studentLesson = LessonDAO.getClassLessonFromStudentId(studentId);

        Map<LocalDate, List<Lesson>> lessonsByDay = new TreeMap<>();
        for (Lesson lesson : studentLesson) {
            LocalDate date = lesson.getLessonStartDate().toLocalDateTime().toLocalDate();
            lessonsByDay.computeIfAbsent(date, k -> new ArrayList<>()).add(lesson);
        }

        try {
            request.setAttribute("studentLesson", lessonsByDay);
            request.getRequestDispatcher("WEB-INF/jsp/pages/studentSchedule.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
