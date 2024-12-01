package com.example.projetjee.controller;

import com.example.projetjee.model.dao.LessonClassesDAO;
import com.example.projetjee.model.dao.LessonDAO;
import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.entities.*;
import com.example.projetjee.util.GMailer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class LessonClassesAssignationServlet.
 * This servlet handles the assignment of lessons to classes and sends notifications to students regarding the new sessions.
 */
@WebServlet(name = "lessonClassesAssignationServlet", value = "/lessonClassesAssignation-servlet")
public class LessonClassesAssignationServlet extends HttpServlet {
    /**
     * Handles the HTTP GET request to display the list of lessons.
     * It retrieves all lessons from the database and forwards them to the JSP page for displaying.
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
     * Handles the HTTP POST request to assign a lesson to a class.
     * This method checks the validity of the selected class and lesson, and assigns the lesson to the class.
     * If the assignment is successful, an email notification is sent to each student in the class.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws IOException if an input or output error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String classeIdString = request.getParameter("classId");
        String lessonIdString = request.getParameter("lessonId");

        int lessonId = Integer.parseInt(lessonIdString);
        request.setAttribute("lessonId", lessonId);

        if(classeIdString == null || classeIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une classe.");
            doGet(request, response);
            return;
        }

        int classeId = Integer.parseInt(classeIdString);

        if(!LessonClassesDAO.canClassParticipate(classeId, lessonId)) {
            request.setAttribute("erreur", "Erreur : La classe a une séance à ces horaires.");
            doGet(request, response);
            return;
        }

        // Create a Lessonclass object and assign the lesson to the class
        Lessonclass lessonclass = new Lessonclass();
        lessonclass.setLessonId(lessonId);
        lessonclass.setClassId(classeId);

        // Add the lesson-class assignment to the database
        if(LessonClassesDAO.addLessonClassInTable(lessonclass)) {
            // Retrieve the list of students in the class
            List<Student> studentsInClass = LessonClassesDAO.getStudentsByClassId(classeId);

            if(studentsInClass != null && !studentsInClass.isEmpty()) {
                String lessonName = LessonClassesDAO.getLessonNameById(lessonId);

                // Send email notifications to all students in the class
                for (Student student : studentsInClass) {
                    // Retrieve the user associated with the student
                    Users user = UserDAO.getUserById(student.getStudentId());
                    String studentEmail = user != null ? user.getUserEmail() : null;

                    if (studentEmail != null) {
                        // Get the user's name
                        String studentUserName = user.getUserName(); // Utiliser le nom de l'utilisateur

                        // Prepare the subject and body of the email
                        String subject = "Nouvelle séance assignée : " + lessonName;
                        String body = "Bonjour " + (studentUserName != null ? studentUserName : "Étudiant")
                                + ",\n\n Une nouvelle séance a été assignée à votre classe pour la matière : " + lessonName + "" +
                                ".\n Veuillez vérifier l'horaire et les détails de la séance dans votre emploi du temps.\n\n" +
                                "Cordialement,\nL'équipe pédagogique";

                        // Send the email using the GMailer
                        try {
                            GMailer gmailer = new GMailer();  // Créer une instance de GMailer
                            gmailer.sendMail(subject, body, studentEmail);  // Envoyer l'email
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            // Forward the request to the lessonClassesManager-servlet
            request.getRequestDispatcher("lessonClassesManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de l'assignation de la classe.");
            doGet(request, response);
        }
    }
}

