package com.example.projetjee.controller;

import com.example.projetjee.model.dao.StudentDAO;
import com.example.projetjee.model.dao.TeacherDAO;
import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.dao.UserToValidateDAO;
import com.example.projetjee.model.entities.Student;
import com.example.projetjee.model.entities.Teacher;
import com.example.projetjee.model.entities.Users;
import com.example.projetjee.model.entities.Userstovalidate;
import com.example.projetjee.util.GMailer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
/**
 * Servlet responsible for validating a user and sending an email to notify them.
 * It processes the validation of users (students or teachers) who are pending validation.
 * After validation, the user is added to the appropriate database table and an email is sent to the user.
 */
@WebServlet(name = "validateUserServletServlet", value = "/validateUser-servlet")

public class ValidateUserServlet extends HttpServlet {
    /**
     * Handles GET requests by forwarding them to the POST method to handle the user validation process.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles POST requests to validate a user, add them to the appropriate table (students or teachers),
     * send a validation email, and remove them from the "users to validate" list.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     * @throws ServletException if the request could not be processed
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userToValidateIdString = request.getParameter("userToValidateId");

        // If no user is selected, display an error and forward to the validation manager page
        if(userToValidateIdString == null || userToValidateIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un utilisateur.");
            request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
            return;
        }

        int userToValidateId = Integer.parseInt(userToValidateIdString);
        // Retrieve the user pending validation from the database
        Userstovalidate userstovalidate = UserToValidateDAO.getUserToValidateById(userToValidateId);
        if(userstovalidate == null) {
            request.setAttribute("erreur", "Erreur : L'utilisateur n'existe pas.");
            request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
            return;
        }
        // Create a new user object based on the data from the validation request
        Users user = new Users();
        user.setUserPassword(userstovalidate.getUserToValidatePassword());
        user.setUserLastName(userstovalidate.getUserToValidateLastName());
        user.setUserName(userstovalidate.getUserToValidateName());
        user.setUserEmail(userstovalidate.getUserToValidateEmail());
        user.setUserBirthdate(userstovalidate.getUserToValidateBirthdate());
        user.setUserRole(userstovalidate.getUserToValidateRole());

        // Add the user to the database
        String error = UserDAO.addUserInTable(user);
        if(error != null) {
            request.setAttribute("erreur", error);
            request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
            return;
        }

        // Add the user to the correct role table (Student or Teacher)
        switch (user.getUserRole()) {
            case student:
                Student student = new Student();
                student.setStudentId(user.getUserId());
                student.setMajorId(userstovalidate.getUserToValidateMajorId());

                StudentDAO.addStudentInTable(student);
                break;
            case teacher:
                Teacher teacher = new Teacher();
                teacher.setTeacherId(user.getUserId());

                TeacherDAO.addTeacherInTable(teacher);
                break;
            default:
                request.setAttribute("erreur", "Erreur : Erreur dans le rôle de l'utilisateur");
                request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
                return;
        }

        // Email the user to notify them that their registration has been validated
        String subject = "Votre inscription a été validée";
        String body = "Bonjour " + user.getUserName() + ",\n\n" +
                "Nous avons le plaisir de vous informer que votre inscription a été validée.\n" +
                "Vous pouvez maintenant accéder à votre espace étudiant.\n\n" +
                "Cordialement,\nL'équipe pédagogique";
        String email = user.getUserEmail();

        try {
            GMailer gmailer = new GMailer();
            gmailer.sendMail(subject, body, email);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur : Impossible d'envoyer l'email de validation.");
            request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
            return;
        }

        // Remove the user from the "users to validate" table
        if(UserToValidateDAO.deleteUserstovalidateFromTable(userToValidateId) == true) {
            request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
        } else {
            request.setAttribute("erreur", "Erreur : Erreur lors du refus de l'utilisateur.");
            request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
        }
    }
}
