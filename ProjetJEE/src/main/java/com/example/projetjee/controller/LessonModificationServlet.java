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

@WebServlet(name = "lessonModificationServlet", value = "/lessonModification-servlet")
public class LessonModificationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String lessonIdString = request.getParameter("lessonId");
        List<Course> courseList = CourseDAO.getAllCourses();
        List<Teacher> teacherList = TeacherDAO.getAllTeacher();

        if(lessonIdString == null || lessonIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une séance.");
            request.getRequestDispatcher("lessonManager-servlet").forward(request, response);
            return;
        }

        int lessonId = Integer.parseInt(lessonIdString);
        Lesson lesson = LessonDAO.getLessonById(lessonId);

        try {
            request.setAttribute("courses", courseList);
            request.setAttribute("teachers", teacherList);
            request.setAttribute("lesson", lesson);
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
        Lesson lesson = LessonDAO.getLessonById(lessonId);

        if(newStartDate == null || newStartDate.isEmpty()) {
            newStartDate = lesson.getLessonStartDate().toString();
            newStartDate = newStartDate.substring(0, 16);
        }
        else {
            dateModified = true;
        }

        if(newEndDate == null || newEndDate.isEmpty()) {
            newEndDate = lesson.getLessonEndDate().toString();
            newEndDate = newEndDate.substring(0, 16);
        }
        else {
            dateModified = true;
        }

        int newCourseId;

        if(newCourseIdString == null || newCourseIdString.isEmpty()) {
            newCourseId = lesson.getCourseId();
        }
        else {
            newCourseId = Integer.parseInt(newCourseIdString);
        }

        int newTeacherId;

        if(newTeacherIdString == null || newTeacherIdString.isEmpty()) {
            newTeacherId = lesson.getTeacherId();
        }
        else {
            newTeacherId = Integer.parseInt(newTeacherIdString);
            teacherModified = true;
        }

        String erreur = DateUtil.areDatesValid(newStartDate, newEndDate);
        if(dateModified == true && erreur != null) {
            request.setAttribute("erreur", erreur);
            doGet(request, response);
            return;
        }

        if((dateModified == true || teacherModified == true ) && LessonDAO.isLessonPossible(lessonId, newTeacherId, newStartDate, newEndDate) == false) {
            request.setAttribute("erreur", "Erreur : Le professeur a déjà cours à ces dates.");
            doGet(request, response);
            return;
        }

        lesson.setLessonStartDate(DateUtil.dateStringToTimestamp(newStartDate));
        lesson.setLessonEndDate(DateUtil.dateStringToTimestamp(newEndDate));
        lesson.setCourseId(newCourseId);
        lesson.setTeacherId(newTeacherId);

        String error = LessonDAO.modifyLessonFromTable(lesson);
        if(error == null) {
            request.getRequestDispatcher("lessonManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", error);
            doGet(request, response);
        }

    }


}
