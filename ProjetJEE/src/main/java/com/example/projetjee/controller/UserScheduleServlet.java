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
/**
 * Servlet responsible for handling the user schedule.
 * Depending on the user role (student or teacher), it retrieves the schedule and displays it.
 * The schedule is grouped by day and presented in the userSchedule.jsp page.
 */
@WebServlet(name = "userScheduleServlet", value = "/userSchedule-servlet")
public class UserScheduleServlet extends HttpServlet {
    /**
     * Handles GET requests by forwarding to the POST method to process the user's schedule.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     * @throws ServletException if the servlet encounters an error while handling the request
     * @throws IOException if an input/output error occurs during request handling
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles POST requests to process the user's schedule.
     * Retrieves the schedule based on the user's role and displays it in the userSchedule.jsp page.
     * If the user is not logged in, it redirects to the index page.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     * @throws ServletException if the servlet encounters an error while handling the request
     * @throws IOException if an input/output error occurs during request handling
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer userId = (Integer) session.getAttribute("user");
        // Check if the user is logged in
        if(userId == null) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        // If a specific userId is passed in the request, use it
        String userIdForm = request.getParameter("userId");
        if(userIdForm != null) {
            userId = Integer.parseInt(userIdForm);
        }

        Map<LocalDate, List<Lesson>> lessonsByDay = new TreeMap<>();
        Role userRole = UserDAO.getUserById(userId).getUserRole();

        List<Lesson> lessonList;

        // Fetch the lessons based on the user's role
        switch (userRole) {
            case student:
                lessonList = LessonDAO.getStudentLessonFromId(userId);
                break;
            case teacher:
                lessonList = LessonDAO.getTeacherLessonFromId(userId);
                break;
            default:
                System.out.println("Erreur : rôle non supporté.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
        }
        // Group lessons by the day they occur
        for (Lesson lesson : lessonList) {
            LocalDate date = lesson.getLessonStartDate().toLocalDateTime().toLocalDate();
            lessonsByDay.computeIfAbsent(date, k -> new ArrayList<>()).add(lesson);
        }

        try {
            // Set attributes for the userId and the lessons
            request.setAttribute("userIdForm", userId);
            request.setAttribute("lessonList", lessonsByDay);
            request.getRequestDispatcher("WEB-INF/jsp/pages/userSchedule.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
