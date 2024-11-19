package com.example.projetjee.controller;

import java.io.*;
import java.util.List;

import com.example.projetjee.model.dao.StudentDAO;
import com.example.projetjee.model.dao.TeacherDAO;
import com.example.projetjee.model.entities.Student;
import com.example.projetjee.model.entities.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "EntryNoteServlet", value = "/entry-note-servlet")
public class EntryNoteServlet extends HttpServlet {
    int teacherID = 8;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // get all disciplines and classes
        List<String> disciplines = TeacherDAO.getAllDiscipline(teacherID);
        List<String> classes = TeacherDAO.getAllClasses(teacherID);

        System.out.println("Disciplines: " + disciplines);
        System.out.println("Classes: " + classes);

        // set them in Attributes
        request.setAttribute("disciplines",disciplines);
        request.setAttribute("classes", classes);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/addGrade.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String discipline = request.getParameter("discipline");
        String className = request.getParameter("class");

        System.out.println("flag2");
        System.out.println("Discipline: " + discipline + " ; Class: " + className);

        // Validate the criteria
        if (discipline != null && className != null && !discipline.isEmpty() && !className.isEmpty()) {
            // Search for students matching the selected discipline and class
            List<Users> students = StudentDAO.getStudentsByDisciplineAndClass(discipline, className, teacherID);

            // Add the list of students as a request attribute
            request.setAttribute("students", students);
        } else {
            // If no valid criteria, set an empty list or null
            request.setAttribute("students", null);
        }

        // Forward the request to the JSP page to display the results
        request.getRequestDispatcher("WEB-INF/jsp/pages/addGrade.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}