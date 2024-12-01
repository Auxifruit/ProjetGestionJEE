package com.example.projetjee.controller;

import com.example.projetjee.model.dao.UserToValidateDAO;
import com.example.projetjee.model.entities.Userstovalidate;
import com.example.projetjee.util.GMailer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet implementation class RefuseUserServlet.
 * This servlet handles the process of refusing a user's registration request by sending a refusal email
 * and removing the user from the validation table.
 */
@WebServlet(name = "refuseUserServletServlet", value = "/refuseUser-servlet")

public class RefuseUserServlet extends HttpServlet {

    /**
     * Handles GET requests by delegating to the doPost method.
     *
     * @param request  the HttpServletRequest object that contains the request from the client
     * @param response the HttpServletResponse object that contains the response to be sent to the client
     * @throws ServletException if an error occurs during request processing
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles POST requests by refusing the user registration and sending a notification email.
     * The user is also removed from the validation table.
     *
     * @param request  the HttpServletRequest object that contains the request from the client
     * @param response the HttpServletResponse object that contains the response to be sent to the client
     * @throws ServletException if an error occurs during request processing
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userToValidateIdString = request.getParameter("userToValidateId");

        if(userToValidateIdString == null || userToValidateIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un utilisateur.");
            request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
            return;
        }

        int userToValidateId = Integer.parseInt(userToValidateIdString);
        Userstovalidate user = UserToValidateDAO.getUserToValidateById(userToValidateId);

        // If the user is found, send the refusal email and delete the user from the table
        if (user != null) {
            // Prepare the email content
            String subject = "Votre inscription a été refusée";
            String body = "Bonjour " + user.getUserToValidateName() + ",\n\n" +
                    "Nous vous informons que votre inscription a été refusée. Si vous avez des questions, vous pouvez contacter notre équipe.\n\n" +
                    "Cordialement,\nL'équipe pédagogique";
            String email = user.getUserToValidateEmail();

            // Send the email
            try {
                GMailer gmailer = new GMailer();  // Créer une instance de GMailer
                gmailer.sendMail(subject, body, email);  // Envoyer l'email
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("erreur", "Erreur : Impossible d'envoyer l'email de notification.");
                request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
                return;
            }

            // Delete the user from the validation table
            boolean deletionSuccess = UserToValidateDAO.deleteUserstovalidateFromTable(userToValidateId);

            if (deletionSuccess) {
                // If deletion is successful, redirect with a success message
                request.setAttribute("success", "Inscription refusée et notification envoyée à l'étudiant.");
                request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
            } else {
                // If there is an error during deletion, show an error message
                request.setAttribute("erreur", "Erreur : Impossible de refuser l'utilisateur.");
                request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
            }
        } else {
            // If the user is not found in the validation table, show an error message
            request.setAttribute("erreur", "Erreur : Utilisateur introuvable.");
            request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
        }
    }
}
