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

@WebServlet(name = "refuseUserServletServlet", value = "/refuseUser-servlet")

public class RefuseUserServlet extends HttpServlet {

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
        Userstovalidate user = UserToValidateDAO.getUserToValidateById(userToValidateId);

        // Si l'utilisateur est trouvé, envoyer l'email et refuser l'inscription
        if (user != null) {
            // Préparer l'email
            String subject = "Votre inscription a été refusée";
            String body = "Bonjour " + user.getUserToValidateName() + ",\n\n" +
                    "Nous vous informons que votre inscription a été refusée. Si vous avez des questions, vous pouvez contacter notre équipe.\n\n" +
                    "Cordialement,\nL'équipe pédagogique";
            String email = user.getUserToValidateEmail();

            // Envoi de l'email
            try {
                GMailer gmailer = new GMailer();  // Créer une instance de GMailer
                gmailer.sendMail(subject, body, email);  // Envoyer l'email
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("erreur", "Erreur : Impossible d'envoyer l'email de notification.");
                request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
                return;
            }

            // Supprimer l'utilisateur de la table des utilisateurs à valider
            boolean deletionSuccess = UserToValidateDAO.deleteUserstovalidateFromTable(userToValidateId);

            if (deletionSuccess) {
                // Redirection avec un message de succès
                request.setAttribute("success", "Inscription refusée et notification envoyée à l'étudiant.");
                request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
            } else {
                // Gestion des erreurs lors de la suppression
                request.setAttribute("erreur", "Erreur : Impossible de refuser l'utilisateur.");
                request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
            }
        } else {
            // Si l'utilisateur n'est pas trouvé dans la table
            request.setAttribute("erreur", "Erreur : Utilisateur introuvable.");
            request.getRequestDispatcher("userToValidateManager-servlet").forward(request, response);
        }
    }
}
