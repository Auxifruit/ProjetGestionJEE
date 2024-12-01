package com.example.projetjee.controller;

import com.example.projetjee.model.dao.*;
import com.example.projetjee.model.entities.Classes;
import com.example.projetjee.model.entities.Course;
import com.example.projetjee.model.entities.Grade;
import com.example.projetjee.util.GMailer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Servlet that handles the teacher's grade management functionality.
 * It allows a teacher to view the grades of students in their courses,
 * organized by class and course. The servlet retrieves the necessary
 * data from the database, filters and organizes it, and displays it to the teacher.
 */
@WebServlet(name = "teacherGradeServlet", value = "/teacherGrade-servlet")
public class TeacherGradeServlet extends HttpServlet {

    /**
     * Handles GET requests by forwarding them to the POST handler.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     * @throws IOException if an input or output error occurs during the request processing
     * @throws ServletException if the request cannot be processed
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Handles POST requests to display the grades for a teacher's courses.
     * This method retrieves the teacher's ID from the session, fetches the
     * relevant courses and grades from the database, organizes them by class
     * and course, and forwards the data to the grade viewing page.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     * @throws IOException if an input or output error occurs during the request processing
     * @throws ServletException if the request cannot be processed
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        Integer teacherId = (Integer) session.getAttribute("user");

        // If no teacher is logged in, redirect to the index page
        if(teacherId == null) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }


        // If the user ID is provided in the request, update the teacher's ID
        String userIdForm = request.getParameter("userId");
        if(userIdForm != null) {
            teacherId = Integer.parseInt(userIdForm);
        }
        // Fetch the list of all courses and grades for the teacher
        List<Course> courseList = CourseDAO.getAllCourses();
        List<Grade> teacherGradeList = GradeDAO.getGradeByTeacherId(teacherId);

        // Map to hold the grades organized by class
        Map<Classes, List<Grade>> classGradeMap = new HashMap<>();

        // Organize grades by class
        for (Grade grade : teacherGradeList) {
            Classes classe = ClasseDAO.getClasseById(StudentDAO.getStudentById(grade.getStudentId()).getClassId());

            classGradeMap.computeIfAbsent(classe, k -> new ArrayList<>()).add(grade);
        }

        // Map to hold courses -> classGradeMap
        Map<Course, Map<Classes, List<Grade>>> courseClassGradeMap = new HashMap<>();

        for (Course course : courseList) {
            // Filter the grades associated with this course
            Map<Classes, List<Grade>> filteredClassGradeMap = new HashMap<>();

            for (Map.Entry<Classes, List<Grade>> entry : classGradeMap.entrySet()) {
                Classes classe = entry.getKey();
                List<Grade> grades = entry.getValue();

                // Filter grades that belong to the current course
                List<Grade> filteredGrades = new ArrayList<>();
                for (Grade grade : grades) {
                    if (grade.getCourseId() == course.getCourseId()) { // Vérifie si la note appartient au cours
                        filteredGrades.add(grade);
                    }
                }
                // If there are any grades for this class and course, add to the map
                if (!filteredGrades.isEmpty()) {
                    filteredClassGradeMap.put(classe, filteredGrades);
                }
            }
            // If there are grades for this course, add to the courseClassGradeMap
            if (!filteredClassGradeMap.isEmpty()) {
                courseClassGradeMap.put(course, filteredClassGradeMap);
            }
        }

        request.setAttribute("userIdForm", teacherId);
        request.setAttribute("courseClassGradeMap", courseClassGradeMap);
        request.getRequestDispatcher("WEB-INF/jsp/pages/teacherGradeViewer.jsp").forward(request, response);
    }
}
