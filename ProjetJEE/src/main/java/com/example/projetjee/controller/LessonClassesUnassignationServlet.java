package com.example.projetjee.controller;

import com.example.projetjee.model.dao.LessonClassesDAO;
import com.example.projetjee.model.dao.LessonDAO;
import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.entities.Lesson;
import com.example.projetjee.model.entities.Lessonclass;
import com.example.projetjee.model.entities.Student;
import com.example.projetjee.model.entities.Users;
import com.example.projetjee.util.GMailer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class LessonClassesUnassignationServlet.
 * This servlet is responsible for unassigning a class from a lesson and notifying students about the cancellation via email.
 */
@WebServlet(name = "lessonClassesUnassignationServlet", value = "/lessonClassesUnassignation-servlet")
public class LessonClassesUnassignationServlet extends HttpServlet {
    /**
     * Handles the HTTP GET request to display all available lessons.
     * It retrieves all lessons from the database and forwards the data to the lesson manager JSP page.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws IOException if an input or output error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Lesson> lessonList = LessonDAO.getAllLesson();

        request.setAttribute("lessons", lessonList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/lessonManager.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Handles the HTTP POST request to unassign a class from a lesson.
     * It removes the class from the specified lesson, and notifies the students via email about the cancellation of the session.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws IOException if an input or output error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String classeIdString = request.getParameter("classId");
        String lessonIdString = request.getParameter("lessonId");

        int lessonId = Integer.parseInt(lessonIdString);
        request.setAttribute("lessonId", lessonId);

        if(classeIdString == null || classeIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une classe.");
            request.getRequestDispatcher("lessonClassesManager-servlet").forward(request, response);
            return;
        }

        int classeId = Integer.parseInt(classeIdString);
        Lessonclass lessonclass = LessonClassesDAO.getLessonClassByLessonIdAndClassId(lessonId, classeId);

        // If the class is successfully removed from the lesson
        if(LessonClassesDAO.deleteLessonclassFromTable(lessonclass.getId()) == true) {
            // Retrieve the list of students enrolled in the class
            List<Student> studentsInClass = LessonClassesDAO.getStudentsByClassId(classeId);

            if(studentsInClass != null && !studentsInClass.isEmpty()) {
                String lessonName = LessonClassesDAO.getLessonNameById(lessonId);

                // For each student, send an email notification about the cancellation
                for (Student student : studentsInClass) {
                    // Retrieve the user associated with the student
                    Users user = UserDAO.getUserById(student.getStudentId());
                    String studentEmail = user != null ? user.getUserEmail() : null;

                    // If the student's email exists, send the cancellation email
                    if (studentEmail != null) {
                        String studentUserName = user.getUserName();

                        // Prepare the subject and body of the email
                        String subject = "Séance annulée : " + lessonName;
                        String body = "Bonjour " + (studentUserName != null ? studentUserName : "Étudiant") + ",\n\n Nous vous informons que la séance assignée à votre classe pour la matière : " + lessonName + " a été annulée.\n Veuillez vérifier votre emploi du temps pour toute mise à jour.\n\n Cordialement,\nL'équipe pédagogique";
                        // Send the email using the GMailer utility class
                        try {
                            GMailer gmailer = new GMailer();  // Create an instance of GMailer
                            gmailer.sendMail(subject, body, studentEmail);  // Send the email
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            // Redirect to the lesson classes manager servlet
            request.getRequestDispatcher("lessonClassesManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la désaffectation de la classe.");
            request.getRequestDispatcher("lessonClassesManager-servlet").forward(request, response);
        }
    }
}

