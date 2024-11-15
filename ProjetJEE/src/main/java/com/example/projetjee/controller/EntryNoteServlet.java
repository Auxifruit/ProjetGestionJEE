package com.example.projetjee.controller;

import java.io.*;
import java.util.List;

import com.example.projetjee.model.dao.TeacherDAO;
import com.example.projetjee.model.entities.Classe;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.sql.rowset.serial.SerialException;

@WebServlet(name = "EntryNoteServlet", value = "/entry-note-servlet")
public class EntryNoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        int teacherID = 8;
        // les matières enseigner
        List<String> disciplines = TeacherDAO.getAllDiscipline(teacherID);
        request.setAttribute("disciplines",disciplines);
        // les classes associés
        List<String> classes = TeacherDAO.getAllClasses(teacherID);
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