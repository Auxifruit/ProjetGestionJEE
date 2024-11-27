package com.example.projetjee.controller;

import com.example.projetjee.model.dao.LessonDAO;
import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.entities.Lesson;
import com.example.projetjee.model.entities.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@WebServlet(name = "userScheduleServlet", value = "/userSchedule-servlet")
public class UserScheduleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer userId = (Integer) session.getAttribute("user");

        if(userId == null) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        Map<LocalDate, List<Lesson>> lessonsByDay = new TreeMap<>();
        Role userRole = UserDAO.getUserById(userId).getUserRole();

        List<Lesson> lessonList;

        switch (userRole) {
            case student:
                lessonList = LessonDAO.getStudentLessonFromId(userId);
                break;
            case teacher:
                lessonList = LessonDAO.getTeacherLessonFromId(userId);
                break;
            default:
                request.setAttribute("erreur", "Erreur : rôle non supporté.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
        }

        for (Lesson lesson : lessonList) {
            LocalDate date = lesson.getLessonStartDate().toLocalDateTime().toLocalDate();
            lessonsByDay.computeIfAbsent(date, k -> new ArrayList<>()).add(lesson);
        }

        try {
            request.setAttribute("lessonList", lessonsByDay);
            request.getRequestDispatcher("WEB-INF/jsp/pages/userSchedule.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
