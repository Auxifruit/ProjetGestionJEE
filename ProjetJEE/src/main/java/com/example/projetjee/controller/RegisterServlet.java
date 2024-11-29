package com.example.projetjee.controller;

import com.example.projetjee.model.dao.MajorDAO;
import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.dao.UserToValidateDAO;
import com.example.projetjee.model.entities.*;
import com.example.projetjee.util.GMailer;
import com.example.projetjee.util.HashPswdUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Major> majorList = MajorDAO.getAllMajor();

        request.setAttribute("majors", majorList);

        try {
            // Redirige vers la page d'inscription
            request.getRequestDispatcher("/WEB-INF/jsp/pages/register.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String birthdate = request.getParameter("birthdate");
        Role role = Role.valueOf(request.getParameter("role"));
        String majorIdString = request.getParameter("major");

        if((firstName == null || firstName.isEmpty()) || (lastName == null || lastName.isEmpty()) || (email == null || email.isEmpty())
                || (password == null || password.isEmpty()) || (birthdate == null || birthdate.isEmpty()) || (role.equals(Role.student) && (majorIdString == null || majorIdString.isEmpty()))) {
            request.setAttribute("error", "Erreur : Veuillez remplir tous les champs.");
            doGet(request, response);
            return;
        }

        if(UserDAO.isEmailInTable(email) == true) {
            request.setAttribute("error", "Erreur : L'email est déjà utilisée.");
            doGet(request, response);
            return;
        }

        Userstovalidate user = new Userstovalidate();
        user.setUserToValidatePassword(HashPswdUtil.hashPassword(password));
        user.setUserToValidateLastName(lastName);
        user.setUserToValidateName(firstName);
        user.setUserToValidateEmail(email);
        user.setUserToValidateBirthdate(birthdate);
        user.setUserToValidateRole(role);

        if(majorIdString != null && !majorIdString.isEmpty()) {
            int majorId = Integer.parseInt(majorIdString);
            user.setUserToValidateMajorId(majorId);
        }
        else {
            user.setUserToValidateMajorId(null);
        }

        String error = UserToValidateDAO.addUserstovalidateInTable(user);
        if(error != null) {
            request.setAttribute("error", error);
            doGet(request, response);
            return;
        }

        try {
            String subject = "Inscription en attente de validation";
            String body = "Bonjour " + firstName + ",\n\n Votre inscription à notre établissement est en attente de validation. Vous serez informé dès que votre compte sera activé.\n\nCordialement,\nL'équipe pédagogique";

            // Utilisation de GMailer pour envoyer l'email
            GMailer gmailer = new GMailer();
            gmailer.sendMail(subject, body, email);

            // Rediriger vers la page de succès
            request.setAttribute("success", "Inscription réussie, en attente de validation.");
            request.getRequestDispatcher("WEB-INF/jsp/pages/succes.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur : Impossible d'envoyer l'email de confirmation.");
            doGet(request, response);
        }
    }
}
