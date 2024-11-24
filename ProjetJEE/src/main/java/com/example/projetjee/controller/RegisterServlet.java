package com.example.projetjee.controller;

import com.example.projetjee.model.dao.MajorDAO;
import com.example.projetjee.model.dao.StudentDAO;
import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.entities.Major;
import com.example.projetjee.model.entities.Student;
import com.example.projetjee.model.entities.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    public static String hashPassword(String password) {
        try {
            // Créer une instance de MessageDigest pour SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Convertir le mot de passe en tableau de bytes
            byte[] encodedHash = digest.digest(password.getBytes());

            // Convertir le tableau de bytes en chaîne hexadécimale
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // Renvoyer une version tronquée à 50 caractères
            return hexString.substring(0, 49);

        } catch (NoSuchAlgorithmException e) {
            // Gérer l'erreur si l'algorithme n'est pas disponible
            throw new RuntimeException("Erreur : Algorithme SHA-256 non disponible", e);
        }
    }


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

        String hashedPassword = hashPassword(password);

        if((firstName == null || firstName.isEmpty()) || (lastName == null || lastName.isEmpty()) || (email == null || email.isEmpty())
                || (password == null || password.isEmpty()) || (birthdate == null || birthdate.isEmpty()) || (majorIdString == null || majorIdString.isEmpty())) {
            request.setAttribute("error", "Erreur : Veuillez remplir tous les champs.");
            doGet(request, response);
            return;
        }

        /*
        // A MODIFIER PLUS TARD
        if(UserDAO.isEmailInTable(email) == true) {
            request.setAttribute("error", "Erreur : Cette adresse e-mail est déjà utilisée.");
            doGet(request, response);
            return;
        }
        */

        Users user = new Users();
        user.setUserPassword(hashedPassword);
        user.setUserLastName(lastName);
        user.setUserName(firstName);
        user.setUserEmail(email);
        user.setUserBirthdate(birthdate);
        user.setRoleId(1);

        String error = UserDAO.addUserInTable(user);
        if(error != null) {
            request.setAttribute("error", error);
            doGet(request, response);
            return;
        }

        user = UserDAO.getUserByEmail(email);
        int majorId = Integer.parseInt(majorIdString);

        Student student = new Student();
        student.setStudentId(user.getUserId());
        student.setMajorId(majorId);

        if(StudentDAO.addStudentInTable(student) == false) {
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
