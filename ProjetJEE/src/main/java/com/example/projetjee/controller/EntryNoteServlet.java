package com.example.projetjee.controller;

import java.io.*;
import java.nio.file.FileStore;
import java.util.List;

import com.example.projetjee.model.dao.StudentDAO;
import com.example.projetjee.model.dao.TeacherDAO;
import com.example.projetjee.model.entities.Classes;
import com.example.projetjee.model.entities.Course;
import com.example.projetjee.model.entities.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "EntryNoteServlet", value = "/entry-note-servlet")
public class EntryNoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int teacherID = Integer.parseInt(request.getParameter("userId"));
        setCommonAttributes(request,teacherID);
        request.setAttribute("teacherID", teacherID);
        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/addGrade.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int teacherID = Integer.parseInt(request.getParameter("teacherID"));
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int classId = Integer.parseInt(request.getParameter("classId"));

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

    private void setCommonAttributes(HttpServletRequest request, int teacherID) {
        // get disciplines and classes and set them as attributes
        List<Course> disciplines = TeacherDAO.getAllDiscipline(teacherID);
        List<Classes> classes = TeacherDAO.getAllClasses(teacherID);

        request.setAttribute("disciplines", disciplines);
        request.setAttribute("classes", classes);
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}