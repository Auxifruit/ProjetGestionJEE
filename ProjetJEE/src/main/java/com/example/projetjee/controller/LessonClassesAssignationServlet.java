package com.example.projetjee.controller;

import com.example.projetjee.model.dao.LessonClassesDAO;
import com.example.projetjee.model.dao.UserDAO;
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

@WebServlet(name = "lessonClassesAssignationServlet", value = "/lessonClassesAssignation-servlet")
public class LessonClassesAssignationServlet extends HttpServlet {

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

        if(LessonClassesDAO.canClassParticipate(classeId, lessonId) == false) {
            request.setAttribute("erreur", "Erreur : La classe a une séance à ces horaires.");
            request.getRequestDispatcher("lessonClassesManager-servlet").forward(request, response);
            return;
        }

        Lessonclass lessonclass = new Lessonclass();
        lessonclass.setLessonId(lessonId);
        lessonclass.setClassId(classeId);

        if(LessonClassesDAO.addLessonClassInTable(lessonclass) == true) {
            // Récupérer la liste des étudiants inscrits à cette classe
            List<Student> studentsInClass = LessonClassesDAO.getStudentsByClassId(classeId);

            if(studentsInClass != null && !studentsInClass.isEmpty()) {
                String lessonName = LessonClassesDAO.getLessonNameById(lessonId);

                // Pour chaque étudiant, envoyer un email pour lui notifier de la nouvelle séance
                for (Student student : studentsInClass) {
                    // Récupérer l'utilisateur associé à cet étudiant
                    Users user = UserDAO.getUserById(student.getStudentId());
                    String studentEmail = user != null ? user.getUserEmail() : null;

                    if (studentEmail != null) {
                        // Récupérer le nom de l'utilisateur
                        String studentUserName = user.getUserName(); // Utiliser le nom de l'utilisateur

                        // Préparer le sujet et le corps du message
                        String subject = "Nouvelle séance assignée : " + lessonName;
                        String body = "Bonjour " + (studentUserName != null ? studentUserName : "Étudiant")
                                + ",\n\n Une nouvelle séance a été assignée à votre classe pour la matière : " + lessonName + "" +
                                ".\n Veuillez vérifier l'horaire et les détails de la séance dans votre emploi du temps.\n\n" +
                                "Cordialement,\nL'équipe pédagogique";

                        // Envoi de l'email via la classe GMailer
                        try {
                            GMailer gmailer = new GMailer();  // Créer une instance de GMailer
                            gmailer.sendMail(subject, body, studentEmail);  // Envoyer l'email
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            // Rediriger vers le gestionnaire des séances
            request.getRequestDispatcher("lessonClassesManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de l'assignation de la classe.");
            request.getRequestDispatcher("lessonClassesManager-servlet").forward(request, response);
        }
    }
}

