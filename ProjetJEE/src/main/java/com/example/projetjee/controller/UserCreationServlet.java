package com.example.projetjee.controller;

import com.example.projetjee.model.dao.AdminDAO;
import com.example.projetjee.model.dao.StudentDAO;
import com.example.projetjee.model.dao.TeacherDAO;
import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.entities.*;
import com.example.projetjee.util.GMailer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "userCreationServlet", value = "/userCreation-servlet")
public class UserCreationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Users> usersList = UserDAO.getAllUsers();

        try {
            request.setAttribute("users", usersList);
            request.getRequestDispatcher("WEB-INF/jsp/pages/userCreation.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newUserLastName = request.getParameter("newUserLastName");
        String newUserName = request.getParameter("newUserName");
        String newUserEmail = request.getParameter("newUserEmail");
        String newUserPassword = request.getParameter("newUserPassword");
        String newUserBirthdate = request.getParameter("newUserBirthdate");
        String newUserRole = request.getParameter("newUserRole");

        if((newUserLastName == null || newUserLastName.isEmpty()) || (newUserName == null || newUserName.isEmpty()) || (newUserEmail == null || newUserEmail.isEmpty())
        || (newUserPassword == null || newUserPassword.isEmpty()) || (newUserBirthdate == null || newUserBirthdate.isEmpty()) || (newUserRole == null || newUserRole.isEmpty())) {
                request.setAttribute("erreur", "Erreur : Veuillez remplir tous les champs.");
                doGet(request, response);
                return;
        }

        Users user = new Users();
        user.setUserLastName(newUserLastName);
        user.setUserName(newUserName);
        user.setUserEmail(newUserEmail);
        user.setUserPassword(newUserPassword);
        user.setUserBirthdate(newUserBirthdate);
        user.setUserRole(Role.valueOf(newUserRole));

        String error = UserDAO.addUserInTable(user);
        if(error != null) {
            request.setAttribute("erreur", error);
            doGet(request, response);
            return;
        }

        user = UserDAO.getUserByEmail(newUserEmail);

        switch (user.getUserRole()) {
            case student:
                Student student = new Student();
                student.setStudentId(user.getUserId());

                if(StudentDAO.addStudentInTable(student) == false) {
                    request.setAttribute("erreur", "Erreur : Erreur lors de l'ajout de l'utilisateur en tant que " + Role.student);
                    doGet(request, response);
                    return;
                }

                String subject = "Bienvenue à l'école";
                String body = "Bonjour " + UserDAO.getUserById(student.getStudentId()).getUserName() + ",\n\n"
                        + "Nous avons le plaisir de vous informer que vous avez été ajouté(e) à notre école en tant qu'étudiant(e).\n\n"
                        + "Cordialement,\nL'équipe pédagogique";

                String email = UserDAO.getUserById(student.getStudentId()).getUserEmail();  // Récupérer l'email de l'étudiant

                try {
                    // Envoyer l'email de notification
                    GMailer gmailer = new GMailer();
                    gmailer.sendMail(subject, body, email);  // Subject, Body, Email
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("erreur", "Erreur : Impossible d'envoyer l'email de bienvenue.");
                    return;
                }

                break;
            case teacher:
                Teacher teacher = new Teacher();
                teacher.setTeacherId(user.getUserId());

                if(TeacherDAO.addTeacherInTable(teacher) == false) {
                    request.setAttribute("erreur", "Erreur : Erreur lors de l'ajout de l'utilisateur en tant que " + Role.teacher);
                    doGet(request, response);
                    return;
                }
                break;
            case administrator:
                Administrator administrator = new Administrator();
                administrator.setAdministratorId(user.getUserId());

                if(AdminDAO.addAdminInTable(administrator) == false) {
                    request.setAttribute("erreur", "Erreur : Erreur lors de l'ajout de l'utilisateur en tant que " + Role.administrator);
                    doGet(request, response);
                    return;
                }
                break;
            default:
                request.setAttribute("erreur", "Erreur : Erreur lors de l'ajout de l'utilisateur en tant que " + user.getUserRole());
                doGet(request, response);
                return;
        }

        try {
            request.getRequestDispatcher("userManager-servlet?roleFilter="+user.getUserRole()).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
