package com.example.projetjee.controller;

import com.example.projetjee.model.dao.MajorDAO;
import com.example.projetjee.model.dao.StudentDAO;
import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.dao.UserToValidateDAO;
import com.example.projetjee.model.entities.*;
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
        user.setUserToValidatePassword(password);
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
            request.setAttribute("success", "Inscription réussie !");
            request.getRequestDispatcher("WEB-INF/jsp/pages/succes.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
