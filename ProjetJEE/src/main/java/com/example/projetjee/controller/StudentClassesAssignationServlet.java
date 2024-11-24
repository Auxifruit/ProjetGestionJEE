package com.example.projetjee.controller;

import com.example.projetjee.model.dao.LessonClassesDAO;
import com.example.projetjee.model.dao.StudentDAO;
import com.example.projetjee.model.entities.Lessonclass;
import com.example.projetjee.model.entities.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "studentClassesAssignationServlet", value = "/studentClassesAssignation-servlet")
public class StudentClassesAssignationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String classeIdString = request.getParameter("classesId");
        String studentIdString = request.getParameter("studentId");

        int classeId = Integer.parseInt(classeIdString);
        System.out.println(classeId);
        request.setAttribute("classesId", classeId);

        if(studentIdString == null || studentIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un étudiant.");
            request.getRequestDispatcher("studentClassesManager-servlet").forward(request, response);
            return;
        }

        int studentId = Integer.parseInt(studentIdString);
        Student student = StudentDAO.getStudentById(studentId);
        student.setClassId(classeId);

        String error = StudentDAO.modifyStudentFromTable(student);
        if(error == null) {
            request.getRequestDispatcher("studentClassesManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", error);
            request.getRequestDispatcher("studentClassesManager-servlet").forward(request, response);
        }
    }
}

