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

/**
 * Servlet handling the management of student-class assignments.
 * It allows viewing available students who do not yet belong to any class and assigning them to a specific class.
 */
@WebServlet(name = "studentClassesManagerServlet", value = "/studentClassesManager-servlet")
public class StudentClassesManagerServlet extends HttpServlet {

    /**
     * Handles the GET request to show the available students and class details.
     * If a valid class ID is provided, it fetches the class and a list of students without any class assignments.
     *
     * @param request the HttpServletRequest object that contains the request from the client
     * @param response the HttpServletResponse object that will contain the response to the client
     * @throws IOException if an input or output error occurs
     * @throws ServletException if the request for the GET cannot be handled
     */
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

    /**
     * Handles the POST request, which redirects to the GET method.
     *
     * @param request the HttpServletRequest object that contains the request from the client
     * @param response the HttpServletResponse object that will contain the response to the client
     * @throws IOException if an input or output error occurs
     * @throws ServletException if the request for the POST cannot be handled
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        doGet(request, response);
    }
}