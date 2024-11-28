//package com.example.projetjee.controller;
//
//import com.example.projetjee.model.dao.LessonClassesDAO;
//import com.example.projetjee.model.dao.LessonDAO;
//import com.example.projetjee.model.dao.UserDAO;
//import com.example.projetjee.model.entities.Student;
//import com.example.projetjee.util.GMailer;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet(name = "lessonDeletionServlet", value = "/lessonDeletion-servlet")
//public class LessonDeletionServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        doPost(request, response);
//    }
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String lessonIdString = request.getParameter("lessonId");
//
//        if(lessonIdString == null || lessonIdString.isEmpty()) {
//            request.setAttribute("erreur", "Erreur : Veuillez choisir une séance.");
//            request.getRequestDispatcher("lessonManager-servlet").forward(request, response);
//            return;
//        }
//
//        int lessonId = Integer.parseInt(lessonIdString);
//
//        if(LessonDAO.getLessonById(lessonId) == null) {
//            request.setAttribute("erreur", "Erreur : La séance n'existe pas.");
//            request.getRequestDispatcher("lessonManager-servlet").forward(request, response);
//            return;
//        }
//
//        // Suppression de la séance
//        boolean isDeleted = LessonDAO.deleteLessonFromTable(lessonId);
//
//        if (isDeleted) {
//            // Récupérer tous les étudiants associés à la séance supprimée
//            List<Student> studentsInLesson = LessonClassesDAO.getStudentsByLessonId(lessonId);
//
//            // Pour chaque étudiant, envoyer un email pour l'informer de la suppression de la séance
//            for (Student student : studentsInLesson) {
//                String studentEmail = UserDAO.getUserEmailById(student.getStudentId());
//
//                if (studentEmail != null) {
//                    // Sujet et corps du message
//                    String subject = "Séance supprimée";
//                    String body = "Bonjour,\n\n" +
//                            "Nous vous informons que la séance pour la matière ID " + lessonId + " a été supprimée.\n" +
//                            "Nous vous invitons à consulter votre emploi du temps pour toute mise à jour.\n\n" +
//                            "Cordialement,\nL'équipe pédagogique";
//
//                    // Envoi de l'email via la classe GMailer
//                    try {
//                        GMailer gmailer = new GMailer();  // Créer une instance de GMailer
//                        gmailer.sendMail(subject, body);  // Envoyer l'email
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            // Rediriger vers la page de gestion des séances
//            request.getRequestDispatcher("lessonManager-servlet").forward(request, response);
//        } else {
//            request.setAttribute("erreur", "Erreur : Erreur lors de la suppression de la séance.");
//            request.getRequestDispatcher("lessonManager-servlet").forward(request, response);
//        }
//    }
//
//
//}
