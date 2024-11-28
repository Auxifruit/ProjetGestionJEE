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

@WebServlet(name = "validateUserServletServlet", value = "/validateUser-servlet")

public class ValidateUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userToValidateIdString = request.getParameter("userToValidateId");

        if(userToValidateIdString == null || userToValidateIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un utilisateur.");
            request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
            return;
        }

        int userToValidateId = Integer.parseInt(userToValidateIdString);

        Userstovalidate userstovalidate = UserToValidateDAO.getUserToValidateById(userToValidateId);
        if(userstovalidate == null) {
            request.setAttribute("erreur", "Erreur : L'utilisateur n'existe pas.");
            request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
            return;
        }

        Users user = new Users();
        user.setUserPassword(userstovalidate.getUserToValidatePassword());
        user.setUserLastName(userstovalidate.getUserToValidateLastName());
        user.setUserName(userstovalidate.getUserToValidateName());
        user.setUserEmail(userstovalidate.getUserToValidateEmail());
        user.setUserBirthdate(userstovalidate.getUserToValidateBirthdate());
        user.setUserRole(userstovalidate.getUserToValidateRole());

        String error = UserDAO.addUserInTable(user);
        if(error != null) {
            request.setAttribute("erreur", error);
            request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
            return;
        }

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

        // L'utilisateur est maintenant validé, on peut envoyer l'email
        String subject = "Votre inscription a été validée";
        String body = "Bonjour " + user.getUserName() + ",\n\n" +
                "Nous avons le plaisir de vous informer que votre inscription a été validée.\n" +
                "Vous pouvez maintenant accéder à votre espace étudiant.\n\n" +
                "Cordialement,\nL'équipe pédagogique";
        String email = user.getUserEmail();

        // Envoi de l'email via GMailer
        try {
            GMailer gmailer = new GMailer();
            gmailer.sendMail(subject, body, email);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur : Impossible d'envoyer l'email de validation.");
            request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
            return;
        }

        if(UserToValidateDAO.deleteUserstovalidateFromTable(userToValidateId) == true) {
            request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
        } else {
            request.setAttribute("erreur", "Erreur : Erreur lors du refus de l'utilisateur.");
            request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
        }
    }
}
