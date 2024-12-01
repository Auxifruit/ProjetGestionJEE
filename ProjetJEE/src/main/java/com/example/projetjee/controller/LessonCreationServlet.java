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
/**
 * Servlet implementation class LessonCreationServlet.
 * This servlet is responsible for the creation of lessons, including assigning a course, teacher, and start/end dates.
 * It validates the inputs and ensures that no conflicting lessons are created.
 */
@WebServlet(name = "lessonCreationServlet", value = "/lessonCreation-servlet")
public class LessonCreationServlet extends HttpServlet {
    /**
     * Handles the HTTP GET request to retrieve all lessons, courses, and teachers from the database.
     * It forwards the retrieved data to the lesson creation JSP page.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws IOException if an input or output error occurs
     * @throws ServletException if a servlet error occurs
     */
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
    /**
     * Handles the HTTP POST request to create a new lesson.
     * It validates the input parameters, checks for date conflicts, and creates a lesson entry in the database.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input or output error occurs
     */
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

        if((startDate == null || startDate.isEmpty()) || (endDate == null || endDate.isEmpty())) {
            request.setAttribute("erreur", "Erreur : Veuillez saisir les 2 dates nécessaire pour la création d'une séance.");
            doGet(request, response);
            return;
        }

        // Validate the dates using the DateUtil helper class
        String erreur = DateUtil.areDatesValid(startDate, endDate);
        if(erreur != null) {
            request.setAttribute("erreur", erreur);
            doGet(request, response);
            return;
        }

        // Check if the teacher already has a class scheduled at the same time
        if(LessonDAO.isLessonPossible(null, teacherId, startDate, endDate) == false) {
            request.setAttribute("erreur", "Erreur : Le professeur a déjà cours à ces dates.");
            doGet(request, response);
            return;
        }

        // Create a new Lesson object and populate it with the form data
        Lesson lesson = new Lesson();
        lesson.setLessonStartDate(DateUtil.dateStringToTimestamp(startDate));
        lesson.setLessonEndDate(DateUtil.dateStringToTimestamp(endDate));
        lesson.setCourseId(courseId);
        lesson.setTeacherId(teacherId);

        // Add the lesson to the database
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
