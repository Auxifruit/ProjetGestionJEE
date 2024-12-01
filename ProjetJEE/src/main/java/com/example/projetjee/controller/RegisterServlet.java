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
/**
 * Servlet implementation class RegisterServlet.
 * This servlet handles user registration by collecting user details, validating the input,
 * and sending an email confirmation. If the registration is successful, the user is added to a
 * pending validation list, and a notification email is sent.
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    /**
     * Handles GET requests. It retrieves a list of majors from the database and forwards the request
     * to the registration page.
     *
     * @param request  the HttpServletRequest object that contains the request from the client
     * @param response the HttpServletResponse object that contains the response to be sent to the client
     * @throws ServletException if an error occurs during request processing
     * @throws IOException      if an I/O error occurs
     */@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get all majors from the database
        List<Major> majorList = MajorDAO.getAllMajor();
        // Set the list of majors as a request attribute for the JSP page
        request.setAttribute("majors", majorList);

        try {
            // Forward the request to register JSP page
            request.getRequestDispatcher("/WEB-INF/jsp/pages/register.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Handles POST requests for user registration. It validates the form data, checks for existing email,
     * and if all fields are valid, adds the user to the pending validation list and sends a confirmation email.
     *
     * @param request  the HttpServletRequest object that contains the request from the client
     * @param response the HttpServletResponse object that contains the response to be sent to the client
     * @throws ServletException if an error occurs during request processing
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String birthdate = request.getParameter("birthdate");
        Role role = Role.valueOf(request.getParameter("role"));
        String majorIdString = request.getParameter("major");

        // Validate input fields
        if((firstName == null || firstName.isEmpty()) || (lastName == null || lastName.isEmpty()) || (email == null || email.isEmpty())
                || (password == null || password.isEmpty()) || (birthdate == null || birthdate.isEmpty()) || (role.equals(Role.student) && (majorIdString == null || majorIdString.isEmpty()))) {
            request.setAttribute("error", "Erreur : Veuillez remplir tous les champs.");
            doGet(request, response);
            return;
        }

        // Check if the email is already in use
        if(UserDAO.isEmailInTable(email) == true) {
            request.setAttribute("error", "Erreur : L'email est déjà utilisée.");
            doGet(request, response);
            return;
        }

        // Create a new user to validate
        Userstovalidate user = new Userstovalidate();
        user.setUserToValidatePassword(HashPswdUtil.hashPassword(password));
        user.setUserToValidateLastName(lastName);
        user.setUserToValidateName(firstName);
        user.setUserToValidateEmail(email);
        user.setUserToValidateBirthdate(birthdate);
        user.setUserToValidateRole(role);

        // Set the major if provided
        if(majorIdString != null && !majorIdString.isEmpty()) {
            int majorId = Integer.parseInt(majorIdString);
            user.setUserToValidateMajorId(majorId);
        }
        else {
            user.setUserToValidateMajorId(null);
        }

        // Add the user to the validation table
        String error = UserToValidateDAO.addUserstovalidateInTable(user);
        if(error != null) {
            request.setAttribute("error", error);
            doGet(request, response);
            return;
        }

        try {
            String subject = "Inscription en attente de validation";
            String body = "Bonjour " + firstName + ",\n\n Votre inscription à notre établissement est en attente de validation. Vous serez informé dès que votre compte sera activé.\n\nCordialement,\nL'équipe pédagogique";

            // Send the confirmation email using GMailer
            GMailer gmailer = new GMailer();
            gmailer.sendMail(subject, body, email);

            // Redirect to success page
            request.setAttribute("success", "Inscription réussie, en attente de validation.");
            request.getRequestDispatcher("WEB-INF/jsp/pages/succes.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur : Impossible d'envoyer l'email de confirmation.");
            doGet(request, response);
        }
    }
}
