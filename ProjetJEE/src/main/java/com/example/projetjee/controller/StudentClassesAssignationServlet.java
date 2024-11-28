package com.example.projetjee.controller;

import com.example.projetjee.model.dao.LessonClassesDAO;
import com.example.projetjee.model.dao.StudentDAO;
import com.example.projetjee.model.entities.Lessonclass;
import com.example.projetjee.model.entities.Student;
import com.example.projetjee.util.GMailer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "studentClassesAssignationServlet", value = "/studentClassesAssignation-servlet")
public class StudentClassesAssignationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String classeIdString = request.getParameter("classesId");
        String studentIdString = request.getParameter("studentId");

        int classeId = Integer.parseInt(classeIdString);
        request.setAttribute("classesId", classeId);

        if(studentIdString == null || studentIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un étudiant.");
            request.getRequestDispatcher("studentClassesManager-servlet").forward(request, response);
            return;
        }

        int studentId = Integer.parseInt(studentIdString);
        Student student = StudentDAO.getStudentById(studentId);
        student.setClassId(classeId);

        String error = StudentDAO.modifyStudentFromTable(student);
        if(error == null) {
            // Si l'affectation a réussi, envoyer un email de notification
            String subject = "Nouvelle affectation de classe";
            String body = "Bonjour " + student.getUser().getUserName() + ",\n\n" +
                    "Nous avons le plaisir de vous informer que vous avez été affecté(e) à une nouvelle classe :\n" +
                    "Classe ID : " + classeId + "\n\n" +
                    "Cordialement,\nL'équipe pédagogique";

            String email = student.getUser().getUserEmail();  // Accéder à l'email via getUser().getUserEmail()

            try {
                // Envoyer l'email de notification
                GMailer gmailer = new GMailer();
                gmailer.sendMail(subject, body, email);  // Subject, Body, Email
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("erreur", "Erreur : Impossible d'envoyer l'email de notification.");
                request.getRequestDispatcher("studentClassesManager-servlet").forward(request, response);
                return;
            }

            // Rediriger vers le gestionnaire de classes
            request.getRequestDispatcher("studentClassesManager-servlet").forward(request, response);
        } else {
            // Si l'affectation a échoué, afficher une erreur
            request.setAttribute("erreur", error);
            request.getRequestDispatcher("studentClassesManager-servlet").forward(request, response);
        }
    }
}
