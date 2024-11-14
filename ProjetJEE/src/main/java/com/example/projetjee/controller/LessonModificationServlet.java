package com.example.projetjee.controller;

import com.example.projetjee.model.dao.CourseDAO;
import com.example.projetjee.model.dao.LessonDAO;
import com.example.projetjee.model.dao.TeacherDAO;
import com.example.projetjee.model.entities.Cours;
import com.example.projetjee.model.entities.Enseignant;
import com.example.projetjee.model.entities.Seance;
import com.example.projetjee.util.DateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "lessonModificationServlet", value = "/lessonModification-servlet")
public class LessonModificationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Seance> lessonList = LessonDAO.getAllLessons();
        List<Cours> courseList = CourseDAO.getAllCourses();
        List<Enseignant> teacherList = TeacherDAO.getAllTeachers();

        request.setAttribute("lessons", lessonList);
        request.setAttribute("courses", courseList);
        request.setAttribute("teachers", teacherList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/lessonModification.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lessonIdString = request.getParameter("lessonId");
        String newStartDate = request.getParameter("newStartDate");
        String newEndDate = request.getParameter("newEndDate");
        String newCourseIdString = request.getParameter("newCourseId");
        String newTeacherIdString = request.getParameter("newTeacherId");
        boolean dateModified = false;
        boolean teacherModified = false;

        if(lessonIdString == null || lessonIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une séance.");
            doGet(request, response);
            return;
        }

        if((newStartDate == null || newStartDate.isEmpty()) && (newEndDate == null || newEndDate.isEmpty()) && (newCourseIdString == null || newCourseIdString.isEmpty()) &&  (newTeacherIdString == null || newTeacherIdString.isEmpty())) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir au moins un champ à modifier.");
            doGet(request, response);
            return;
        }

        int lessonId = Integer.parseInt(lessonIdString);

        if(newStartDate == null || newStartDate.isEmpty()) {
            newStartDate = LessonDAO.getLessonStartDate(lessonId);
        }
        else {
            dateModified = true;
        }

        if(newEndDate == null || newEndDate.isEmpty()) {
            newEndDate = LessonDAO.getLessonEndDate(lessonId);
        }
        else {
            dateModified = true;
        }

        int newCourseId;

        if(newCourseIdString == null || newCourseIdString.isEmpty()) {
            newCourseId = LessonDAO.getLessonCourseId(lessonId);
        }
        else {
            newCourseId = Integer.parseInt(newCourseIdString);
        }

        int newTeacherId;

        if(newTeacherIdString == null || newTeacherIdString.isEmpty()) {
            newTeacherId = LessonDAO.getLessonTeacherId(lessonId);
        }
        else {
            newTeacherId = Integer.parseInt(newTeacherIdString);
            teacherModified = true;
        }

        if(dateModified == true && DateUtil.areDatesValid(newStartDate, newEndDate) == false) {
            request.setAttribute("erreur", "Erreur : Veuillez saisir 2 dates valides.");
            doGet(request, response);
            return;
        }

        if((dateModified == true || teacherModified == true ) && LessonDAO.isLessonPossible(newTeacherId, newStartDate, newEndDate) == false) {
            request.setAttribute("erreur", "Erreur : Le professeur a déjà cours à ces dates.");
            doGet(request, response);
            return;
        }

        if(LessonDAO.modifyLessonInTable(lessonId, newStartDate, newEndDate, newCourseId, newTeacherId) == true) {
            doGet(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la modification de la séance.");
            doGet(request, response);
        }

    }


}
