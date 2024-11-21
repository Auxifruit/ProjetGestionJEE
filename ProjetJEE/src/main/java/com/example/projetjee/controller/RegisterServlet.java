package com.example.projetjee.controller;

import com.example.projetjee.model.dao.MajorDAO;
import com.example.projetjee.model.dao.StudentDAO;
import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.entities.Major;
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
        String majorIdString = request.getParameter("major");

        if((firstName == null || firstName.isEmpty()) || (lastName == null || lastName.isEmpty()) || (email == null || email.isEmpty())
                || (password == null || password.isEmpty()) || (birthdate == null || birthdate.isEmpty()) || (majorIdString == null || majorIdString.isEmpty())) {
            request.setAttribute("error", "Erreur : Veuillez remplir tous les champs.");
            doGet(request, response);
            return;
        }

        if(UserDAO.isEmailInTable(email) == true) {
            request.setAttribute("error", "Erreur : Cette adresse e-mail est déjà utilisée.");
            doGet(request, response);
            return;
        }

        if(UserDAO.addUserInTable(password, lastName, firstName, email, birthdate, 1) == false) {
            request.setAttribute("error", "Erreur : Erreur lors de l'inscription.");
            doGet(request, response);
            return;
        }

        int userId = UserDAO.getUserIdByEmail(email);
        int majorId = Integer.parseInt(majorIdString);

        if(StudentDAO.addStudentInTable(userId, null, majorId) == false) {
            request.setAttribute("error", "Erreur : Erreur lors de l'affectation du rôle étudiant, veuillez contacter un administrateur pour vous aider.");
            doGet(request, response);
            return;
        }

        try {
            request.setAttribute("success", "Inscription réussie !");
            request.getRequestDispatcher("WEB-INF/jsp/pages/succes.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
