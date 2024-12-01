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
 * Servlet implementation class StudentClassesAssignationServlet.
 * This servlet handles the assignment of students to classes. It processes the form submission
 * to assign a student to a class and sends a notification email to the student.
 */
@WebServlet(name = "studentClassesAssignationServlet", value = "/studentClassesAssignation-servlet")
public class StudentClassesAssignationServlet extends HttpServlet {

    /**
     * Handles POST requests to assign a student to a class. It validates the input,
     * modifies the student record in the database, and sends an email notification to the student.
     *
     * @param request  the HttpServletRequest object that contains the request from the client
     * @param response the HttpServletResponse object that contains the response to be sent to the client
     * @throws IOException      if an I/O error occurs
     * @throws ServletException if a servlet-related error occurs
     */
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
            // If assignment is successful, send email notification to the student
            String subject = "Nouvelle affectation de classe";
            String body = "Bonjour " + UserDAO.getUserById(student.getStudentId()).getUserName() + ",\n\n"
                    + "Nous avons le plaisir de vous informer que vous avez été affecté(e) à une nouvelle classe :\n"
                    + "Classe ID : " + classeId + "\n\n"
                    + "Cordialement,\nL'équipe pédagogique";

            String email = UserDAO.getUserById(student.getStudentId()).getUserEmail();  // Accéder à l'email via getUser().getUserEmail()

            try {
                GMailer gmailer = new GMailer();
                gmailer.sendMail(subject, body, email);  // Subject, Body, Email
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("erreur", "Erreur : Impossible d'envoyer l'email de notification.");
                request.getRequestDispatcher("studentClassesManager-servlet").forward(request, response);
                return;
            }

            // Redirect to the student classes manager page
            request.getRequestDispatcher("studentClassesManager-servlet").forward(request, response);
        }
        else {
            // If assignment failed, display an error message
            request.setAttribute("erreur", error);
            request.getRequestDispatcher("studentClassesManager-servlet").forward(request, response);
        }
    }
}

