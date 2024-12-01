package com.example.projetjee.controller;

import java.io.*;
import java.nio.file.FileStore;
import java.util.List;

import com.example.projetjee.model.dao.StudentDAO;
import com.example.projetjee.model.dao.ClasseDAO;
import com.example.projetjee.model.dao.CourseDAO;
import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.entities.Classes;
import com.example.projetjee.model.entities.Course;
import com.example.projetjee.model.entities.Role;
import com.example.projetjee.model.entities.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/**
 * Servlet to handle the entry of student grades by the teacher.
 * This servlet allows a teacher to view students in a class and discipline
 * and enter grades for them.
 */
@WebServlet(name = "EntryNoteServlet", value = "/entry-note-servlet")
public class EntryNoteServlet extends HttpServlet {
    /**
     * Handles the GET request for displaying the grade entry page.
     * It verifies if the user is a teacher and retrieves the necessary data
     * to display the grade entry page.
     *
     * @param request the HttpServletRequest containing the request data
     * @param response the HttpServletResponse to send the response
     * @throws IOException if an input or output error occurs
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        Integer teacherID = (Integer) session.getAttribute("user");

        // Check if the user is a teacher, otherwise redirect to the index page
        if(!UserDAO.getUserById(teacherID).getUserRole().equals(Role.teacher)) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        
        setCommonAttributes(request,teacherID);
        request.setAttribute("teacherID", teacherID);
        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/addGrade.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the POST request for entering grades.
     * It validates the received data (course and class ID), retrieves the
     * students for the specified course and class, and forwards the data
     * to the grade entry page.
     *
     * @param request the HttpServletRequest containing the request data
     * @param response the HttpServletResponse to send the response
     * @throws IOException if an input or output error occurs
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String teacherIdString = request.getParameter("teacherID");
        String courseIdString = request.getParameter("courseId");
        String classIdString = request.getParameter("classId");

        if((courseIdString == null || courseIdString.isEmpty()) || (classIdString == null || classIdString.isEmpty())) {
            response.sendRedirect("entry-note-servlet?userId=" + teacherIdString);
            return;
        }

        int teacherID = Integer.parseInt(teacherIdString);
        int courseId = Integer.parseInt(courseIdString);
        int classId = Integer.parseInt(classIdString);

        System.out.println("ID Discipline: " + courseId + " ; ID Class: " + classId);

        // Validate the criteria
        List<Users> students = StudentDAO.getStudentsByDisciplineAndClass(courseId, classId, teacherID);

        // If no valid criteria, set an empty list or null
        request.setAttribute("students", students);
        // set the CourseId, with the CourseName(Discipline) to sent it in parameters when we create a grade
        request.setAttribute("courseId", courseId);
        // reset the communes attributes before going back to the jsp
        setCommonAttributes(request,teacherID);
        // set back teacherID
        request.setAttribute("teacherID", teacherID);
        // Forward the request to the JSP page to display the results
        request.getRequestDispatcher("WEB-INF/jsp/pages/addGrade.jsp").forward(request, response);
    }

    /**
     * Sets common attributes such as available courses and classes for the teacher.
     *
     * @param request the HttpServletRequest containing the request data
     * @param teacherID the ID of the teacher
     */
    private void setCommonAttributes(HttpServletRequest request, int teacherID) {
        // get disciplines and classes and set them as attributes
        List<Course> disciplines = CourseDAO.getAllTeacherCourseByTeacherId(teacherID);
        List<Classes> classes = ClasseDAO.getAllTeacherClassByTeacherId(teacherID);

        request.setAttribute("disciplines", disciplines);
        request.setAttribute("classes", classes);
    }

    /**
     * Cleanup method called when the servlet is destroyed.
     * This method can be used to release resources if necessary.
     */
    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}
