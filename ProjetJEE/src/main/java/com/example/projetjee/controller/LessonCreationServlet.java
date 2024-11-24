package com.example.projetjee.controller;

import com.example.projetjee.model.dao.CourseDAO;
import com.example.projetjee.model.dao.LessonDAO;
import com.example.projetjee.model.dao.TeacherDAO;
import com.example.projetjee.model.entities.Course;
import com.example.projetjee.model.entities.Teacher;
import com.example.projetjee.model.entities.Lesson;
import com.example.projetjee.util.DateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet(name = "lessonCreationServlet", value = "/lessonCreation-servlet")
public class LessonCreationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Lesson> lessonList = LessonDAO.getAllLesson();
        List<Course> courseList = CourseDAO.getAllCourses();
        List<Teacher> teacherList = TeacherDAO.getAllTeacher();

        request.setAttribute("lessons", lessonList);
        request.setAttribute("courses", courseList);
        request.setAttribute("teachers", teacherList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/lessonCreation.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseIdString = request.getParameter("course");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String teacherIdString = request.getParameter("teacher");

        if(courseIdString == null || courseIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un cours.");
            doGet(request, response);
            return;
        }

        if(teacherIdString == null || teacherIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un enseignant.");
            doGet(request, response);
            return;
        }

        int courseId = Integer.parseInt(courseIdString);
        int teacherId = Integer.parseInt(teacherIdString);

        if(startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez saisir les 2 dates nécessaire pour la création d'une séance.");
            doGet(request, response);
            return;
        }

        String erreur = DateUtil.areDatesValid(startDate, endDate);
        if(erreur != null) {
            request.setAttribute("erreur", erreur);
            doGet(request, response);
            return;
        }

        if(LessonDAO.isLessonPossible(null, teacherId, startDate, endDate) == false) {
            request.setAttribute("erreur", "Erreur : Le professeur a déjà cours à ces dates.");
            doGet(request, response);
            return;
        }

        Lesson lesson = new Lesson();
        lesson.setLessonStartDate(DateUtil.dateStringToTimestamp(startDate));
        lesson.setLessonEndDate(DateUtil.dateStringToTimestamp(endDate));
        lesson.setCourseId(courseId);
        lesson.setTeacherId(teacherId);

        String error = LessonDAO.addLessonInTable(lesson);
        if(error == null) {
            request.getRequestDispatcher("lessonManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", error);
            doGet(request, response);
        }
    }


}
