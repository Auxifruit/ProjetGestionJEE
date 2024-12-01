package com.example.projetjee.controller;

import com.example.projetjee.model.dao.LessonClassesDAO;
import com.example.projetjee.model.dao.LessonDAO;
import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.entities.Student;
import com.example.projetjee.util.GMailer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
/**
 * Servlet implementation class LessonDeletionServlet.
 * This servlet handles the deletion of lessons from the system.
 * When a lesson is deleted, it also notifies students via email.
 */
@WebServlet(name = "lessonDeletionServlet", value = "/lessonDeletion-servlet")
public class LessonDeletionServlet extends HttpServlet {
    /**
     * Handles the HTTP GET request for lesson deletion by forwarding it to the doPost method.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws IOException if an input or output error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP POST request for lesson deletion.
     * It validates the lesson ID, deletes the lesson, and sends email notifications to students.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input or output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lessonIdString = request.getParameter("lessonId");

        if(lessonIdString == null || lessonIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une séance.");
            request.getRequestDispatcher("lessonManager-servlet").forward(request, response);
            return;
        }

        int lessonId = Integer.parseInt(lessonIdString);

        if(LessonDAO.getLessonById(lessonId) == null) {
            request.setAttribute("erreur", "Erreur : La séance n'existe pas.");
            request.getRequestDispatcher("lessonManager-servlet").forward(request, response);
            return;
        }

        // Delete the lesson from the database
        boolean isDeleted = LessonDAO.deleteLessonFromTable(lessonId);
        if(isDeleted) {
            // Retrieve all students associated with the deleted lesson
            List<Student> studentsInLesson = LessonClassesDAO.getStudentsByLessonId(lessonId);

            if(studentsInLesson != null && !studentsInLesson.isEmpty()) {
                // Email each student to inform them
                for (Student student : studentsInLesson) {
                    String studentEmail = UserDAO.getUserEmailById(student.getStudentId());

                    if (studentEmail != null) {
                        // Prepare the email subject and body
                        String subject = "Séance supprimée";
                        String body = "Bonjour,\n\n"
                                + "Nous vous informons que la séance pour la matière ID " + lessonId + " a été supprimée.\n"
                                + "Nous vous invitons à consulter votre emploi du temps pour toute mise à jour.\n\n"
                                + "Cordialement,\nL'équipe pédagogique";

                        // Send the email via the GMailer utility class
                        try {
                            GMailer gmailer = new GMailer();
                            gmailer.sendMail(subject, body, studentEmail);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            // Redirect to the lesson manager page
            request.getRequestDispatcher("lessonManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la suppression de la séance.");
            request.getRequestDispatcher("lessonManager-servlet").forward(request, response);
        }
    }


}
