package com.example.projetjee.controller;

import com.example.projetjee.model.dao.ClasseDAO;
import com.example.projetjee.model.dao.LessonDAO;
import com.example.projetjee.model.dao.StudentDAO;
import com.example.projetjee.model.entities.Classes;
import com.example.projetjee.model.entities.Lesson;
import com.example.projetjee.model.entities.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "studentClassesManagerServlet", value = "/studentClassesManager-servlet")
public class StudentClassesManagerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String classIdString = request.getParameter("classesId");
        List<Student> availableStudentList;

        if(classIdString == null || classIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une classe.");
            request.getRequestDispatcher("classesManager-servlet").forward(request, response);
            return;
        }

        int classId = Integer.parseInt(classIdString);

        Classes classe = ClasseDAO.getClasseById(classId);
        availableStudentList = StudentDAO.getStudentListWithoutClasses();

        request.setAttribute("classe", classe);
        request.setAttribute("availableStudents", availableStudentList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/studentClassesManager.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        doGet(request, response);
    }
}