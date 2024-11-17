package com.example.projetjee.controller;

import java.io.*;
import java.util.List;

import com.example.projetjee.model.dao.TeacherDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "ListStudentEntryNoteServlet", value = "/list-student-entry-note-servlet")
public class ListStudentEntryNoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        int teacherID = 8;
        // get all student with the 2 criteria
        List<String> disciplines = TeacherDAO.getAllDiscipline(teacherID);
        List<String> classes = TeacherDAO.getAllClasses(teacherID);

        // set them in Attributes
        request.setAttribute("disciplines",disciplines);
        request.setAttribute("classes", classes);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/addGrade.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }
}
