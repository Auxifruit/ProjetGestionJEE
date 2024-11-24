package com.example.projetjee.controller;

import com.example.projetjee.model.dao.CourseDAO;
import com.example.projetjee.model.dao.SubjectDAO;
import com.example.projetjee.model.entities.Course;
import com.example.projetjee.model.entities.Subjects;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "courseCreationServlet", value = "/courseCreation-servlet")
public class CourseCreationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Course> courseList = CourseDAO.getAllCourses();
        List<Subjects> subjectList = SubjectDAO.getAllSubjects();

        request.setAttribute("courses", courseList);
        request.setAttribute("subjects", subjectList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/courseCreation.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectIdString = request.getParameter("courseSubjectId");
        String courseName = request.getParameter("courseName");

        if(subjectIdString == null || subjectIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une matière.");
            doGet(request, response);
            return;
        }

        if(courseName == null || courseName.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez saisir le nom du nouveau cours.");
            doGet(request, response);
            return;
        }

        int subjectId = Integer.parseInt(subjectIdString);

        Course course = new Course();
        course.setCourseName(courseName);
        course.setSubjectId(subjectId);

        String error = CourseDAO.addCourseInTable(course);
        if(error == null) {
            request.getRequestDispatcher("courseManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", error);
            doGet(request, response);
        }

    }


}
