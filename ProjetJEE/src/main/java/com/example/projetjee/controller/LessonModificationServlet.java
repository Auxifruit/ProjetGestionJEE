package com.example.projetjee.controller;

import com.example.projetjee.model.dao.*;
import com.example.projetjee.model.entities.Course;
import com.example.projetjee.model.entities.Student;
import com.example.projetjee.model.entities.Teacher;
import com.example.projetjee.model.entities.Lesson;
import com.example.projetjee.util.DateUtil;
import com.example.projetjee.util.GMailer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Servlet implementation class LessonModificationServlet.
 * This servlet handles the modification of lesson details such as the date, course, and teacher.
 * It allows users to modify a lesson and informs the enrolled students of the changes via email.
 */
@WebServlet(name = "lessonModificationServlet", value = "/lessonModification-servlet")
public class LessonModificationServlet extends HttpServlet {
    /**
     * Handles the HTTP GET request to retrieve the lesson data and display the lesson modification page.
     * It fetches the lesson, course, and teacher information, and forwards the request to the modification page.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
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
    /**
     * Handles the HTTP POST request to modify the lesson details.
     * It updates the lesson with new information (start date, end date, course, and teacher),
     * and sends email notifications to the enrolled students if there is a change.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
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

        // Handle date modification (start and end dates)
        if(newStartDate == null || newStartDate.isEmpty()) {
            newStartDate = lesson.getLessonStartDate().toString();
            newStartDate = newStartDate.substring(0, 16);
            newStartDate = newStartDate.replace(" ", "T");
        }
        else {
            dateModified = true;
        }

        if(newEndDate == null || newEndDate.isEmpty()) {
            newEndDate = lesson.getLessonEndDate().toString();
            newEndDate = newEndDate.substring(0, 16);
            newEndDate = newEndDate.replace(" ", "T");
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

        if(dateModified == true) {
            String erreur = DateUtil.areDatesValid(newStartDate, newEndDate);
            if(erreur != null) {
                request.setAttribute("erreur", erreur);
                doGet(request, response);
                return;
            }
        }

        if((dateModified == true || teacherModified == true ) && LessonDAO.isLessonPossible(lessonId, newTeacherId, newStartDate, newEndDate) == false) {
            request.setAttribute("erreur", "Erreur : Le professeur a déjà cours à ces dates.");
            doGet(request, response);
            return;
        }

        if(dateModified == true) {
            lesson.setLessonStartDate(DateUtil.dateStringToTimestamp(newStartDate));
            lesson.setLessonEndDate(DateUtil.dateStringToTimestamp(newEndDate));
        }
        lesson.setCourseId(newCourseId);
        lesson.setTeacherId(newTeacherId);

        String error = LessonDAO.modifyLessonFromTable(lesson);
        if(error == null) {
            // Send email notifications to students
            List<Student> studentsInLesson = LessonClassesDAO.getStudentsByLessonId(lessonId);
            System.out.println("studentsInLesson size : " + studentsInLesson.size());

            if(studentsInLesson != null && !studentsInLesson.isEmpty()) {
                // Pour chaque étudiant, envoyer un email de notification
                for (Student student : studentsInLesson) {
                    String studentEmail = UserDAO.getUserEmailById(student.getStudentId());

                    if (studentEmail != null) {
                        String subject = "Modification de votre séance";
                        String body = "Bonjour,\n\n"
                                + "Nous vous informons que la séance pour la matière " + lesson.getCourseId() + " a été modifiée.\n"
                                + "Nouvelle date : " + newStartDate + " - " + newEndDate + "\n\n"
                                + "Cordialement,\nL'équipe pédagogique";

                        try {
                            GMailer gmailer = new GMailer();
                            gmailer.sendMail(subject, body, studentEmail);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            // Redirect to lesson manager page
            request.getRequestDispatcher("lessonManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", error);
            doGet(request, response);
        }

    }


}
