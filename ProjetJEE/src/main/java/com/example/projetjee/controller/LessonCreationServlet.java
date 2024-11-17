package com.example.projetjee.controller;

import com.example.projetjee.model.dao.CourseDAO;
import com.example.projetjee.model.dao.LessonDAO;
import com.example.projetjee.model.dao.TeacherDAO;
import com.example.projetjee.util.DateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "lessonCreationServlet", value = "/lessonCreation-servlet")
public class LessonCreationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Cours> courseList = CourseDAO.getAllCourses();
        List<Enseignant> teacherList = TeacherDAO.getAllTeachers();

        request.setAttribute("courses", courseList);
        request.setAttribute("teachers", teacherList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/creationLesson.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int courseId = Integer.parseInt(request.getParameter("course"));
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        int teacherId = Integer.parseInt(request.getParameter("teacher"));

        if(startDate == null || startDate.trim().isEmpty() || endDate == null || endDate.trim().isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez saisir les 2 dates nécessaire pour la création d'une séance.");
            doGet(request, response);
            return;
        }
        if(DateUtil.areDatesValid(startDate, endDate) == false) {
            request.setAttribute("erreur", "Erreur : Veuillez saisir 2 dates valides.");
            doGet(request, response);
            return;
        }
        if(LessonDAO.isLessonPossible(teacherId, startDate, endDate) == false) {
            request.setAttribute("erreur", "Erreur : Le professeur a déjà cours à ces dates.");
            doGet(request, response);
            return;
        }

        if(LessonDAO.AddLesson(null, startDate, endDate, courseId, teacherId) == true) {
            doGet(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de l'ajout de la séance.");
            doGet(request, response);
        }
    }


}
