package com.example.projetjee.controller;

import com.example.projetjee.model.dao.StudentDAO;
import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.entities.Student;
import com.example.projetjee.util.GMailer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
/**
 * Servlet for managing the unassignment of students from a class.
 * It allows to unassign a student from a class and sends a notification email after a successful unassignment.
 */
@WebServlet(name = "studentClassesUnassignationServlet", value = "/studentClassesUnassignation-servlet")
public class StudentClassesUnassignationServlet extends HttpServlet {

    /**
     * Handles the POST request to unassign a student from a class.
     * If successful, a notification email is sent to the student.
     *
     * @param request the HttpServletRequest object that contains the request from the client
     * @param response the HttpServletResponse object that will contain the response to the client
     * @throws IOException if an input or output error occurs
     * @throws ServletException if the request for the POST cannot be handled
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String classeIdString = request.getParameter("classesId");
        String studentIdString = request.getParameter("studentId");

        int classeId = Integer.parseInt(classeIdString);
        request.setAttribute("classesId", classeId);

        // Check if a student ID is provided
        if(studentIdString == null || studentIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un étudiant.");
            request.getRequestDispatcher("studentClassesManager-servlet").forward(request, response);
            return;
        }

        int studentId = Integer.parseInt(studentIdString);
        Student student = StudentDAO.getStudentById(studentId);
        // Unassign the student from the class
        student.setClassId(null);

        String error = StudentDAO.modifyStudentFromTable(student);
        // Modify the student's data in the database
        if(error == null) {
            String subject = "Désaffectation de classe";
            String body = "Bonjour " + UserDAO.getUserById(student.getStudentId()).getUserName() + ",\n\n"
                    + "Nous avons le regret de vous informer que vous n'êtes plus affecté(e) à la classe suivante :\n"
                    + "Classe ID : " + classeId + "\n\n"
                    + "Cordialement,\nL'équipe pédagogique";

            String email = UserDAO.getUserById(student.getStudentId()).getUserEmail();  // Accéder à l'email de l'étudiant

            try {
                // Send the notification email
                GMailer gmailer = new GMailer();
                gmailer.sendMail(subject, body, email);  // Subject, Body, Email
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("erreur", "Erreur : Impossible d'envoyer l'email de notification.");
                request.getRequestDispatcher("studentClassesManager-servlet").forward(request, response);
                return;
            }
            // Redirect back to the class manager
            request.getRequestDispatcher("studentClassesManager-servlet").forward(request, response);
        }
        else {
            // If unassignment failed, display an error
            request.setAttribute("erreur", error);
            request.getRequestDispatcher("studentClassesManager-servlet").forward(request, response);
        }
    }
}

