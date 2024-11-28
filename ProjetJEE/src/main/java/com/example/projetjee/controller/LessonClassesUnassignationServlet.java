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

@WebServlet(name = "lessonClassesUnassignationServlet", value = "/lessonClassesUnassignation-servlet")
public class LessonClassesUnassignationServlet extends HttpServlet {

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

        if(LessonClassesDAO.deleteLessonclassFromTable(lessonclass.getId()) == true) {
            // Récupérer la liste des étudiants inscrits à cette classe
            List<Student> studentsInClass = LessonClassesDAO.getStudentsByClassId(classeId);
            String lessonName = LessonClassesDAO.getLessonNameById(lessonId);

            // Pour chaque étudiant, envoyer un email pour lui notifier que la séance a été annulée
            for (Student student : studentsInClass) {
                // Récupérer l'utilisateur associé à cet étudiant
                Users user = UserDAO.getUserByStudentId(student.getStudentId());
                String studentEmail = user != null ? user.getUserEmail() : null;

                if (studentEmail != null) {
                    // Récupérer le nom de l'utilisateur
                    String studentUserName = user.getUserName(); // Utiliser le nom de l'utilisateur

                    // Préparer le sujet et le corps du message
                    String subject = "Séance annulée : " + lessonName;
                    String body = "Bonjour " + (studentUserName != null ? studentUserName : "Étudiant") + ",\n\n" +
                            "Nous vous informons que la séance assignée à votre classe pour la matière : " +
                            lessonName + " a été annulée.\n" +
                            "Veuillez vérifier votre emploi du temps pour toute mise à jour.\n\n" +
                            "Cordialement,\nL'équipe pédagogique";

                    // Envoi de l'email via la classe GMailer
                    try {
                        GMailer gmailer = new GMailer();  // Créer une instance de GMailer
                        gmailer.sendMail(studentEmail, subject, body);  // Envoyer l'email
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // Rediriger vers le gestionnaire des séances
            request.getRequestDispatcher("lessonClassesManager-servlet").forward(request, response);
        } else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la désaffectation de la classe.");
            request.getRequestDispatcher("lessonClassesManager-servlet").forward(request, response);
        }
    }
}
