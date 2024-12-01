package com.example.projetjee.controller;

import com.example.projetjee.model.dao.StudentDAO;
import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.entities.Role;
import com.example.projetjee.model.entities.Student;
import com.example.projetjee.model.entities.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet responsible for handling the modification of user details.
 * It allows users to update their personal information, such as last name, first name, email, birthdate, role, and for students, their class and major.
 * This servlet handles both GET and POST requests for modifying user details.
 */
@WebServlet(name = "userModificationServlet", value = "/userModification-servlet")
public class UserModificationServlet extends HttpServlet {
    /**
     * Handles GET requests to display the user modification page.
     * Retrieves the user based on the provided userId and sets the user as a request attribute.
     * If no userId is provided, an error message is displayed.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     * @throws ServletException if the servlet encounters an error while handling the request
     * @throws IOException if an input/output error occurs during request handling
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdString = request.getParameter("userId");
        // Check if the userId parameter is present and valid
        if(userIdString == null || userIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un utilisateur.");
            request.getRequestDispatcher("userManager-servlet?roleFilter=student").forward(request, response);
            return;
        }

        int userId = Integer.parseInt(userIdString);
        Users user = UserDAO.getUserById(userId);

        try {
            // Set the user as a request attribute and forward to the user modification page
            request.setAttribute("user", user);
            request.getRequestDispatcher("WEB-INF/jsp/pages/userModification.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles POST requests to process the user modification form.
     * Validates the form fields, updates the user information in the database, and forwards to the user management page.
     * If there are validation errors or database errors, appropriate error messages are displayed.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object containing the response to be sent to the client
     * @throws ServletException if the servlet encounters an error while handling the request
     * @throws IOException if an input/output error occurs during request handling
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdString = request.getParameter("userId");
        String userNewLastName = request.getParameter("userNewLastName");
        String userNewName = request.getParameter("userNewName");
        String userNewEmail = request.getParameter("userNewEmail");
        String userNewBirthdate = request.getParameter("userNewBirthdate");
        String userNewClassesIdString = request.getParameter("userNewClassesId");
        String userNewMajorIdString = request.getParameter("userNewMajorId");
        String userNewRole = request.getParameter("userNewRole");

        // Check if the userId is provided
        if(userIdString == null || userIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un utilisateur.");
            request.getRequestDispatcher("userManager-servlet?roleFilter=student").forward(request, response);
            return;
        }

        int userId = Integer.parseInt(userIdString);
        Users user = UserDAO.getUserById(userId);

        // Ensure that at least one field is filled
        if((userNewLastName == null || userNewLastName.isEmpty()) && (userNewName == null || userNewName.isEmpty()) && (userNewEmail == null || userNewEmail.isEmpty())
        && (userNewBirthdate == null || userNewBirthdate.isEmpty()) && (userNewRole == null || userNewRole.isEmpty())) {
            if(user.getUserRole().equals(Role.student)){
                if((userNewClassesIdString == null || userNewClassesIdString.isEmpty()) && (userNewMajorIdString == null || userNewMajorIdString.isEmpty())) {
                    request.setAttribute("erreur", "Erreur : Veuillez remplir au moins un champ.");
                    doGet(request, response);
                    return;
                }
            } else {
                request.setAttribute("erreur", "Erreur : Veuillez remplir au moins un champ.");
                doGet(request, response);
                return;
            }
        }
        // Update the user information based on the provided parameters
        if(userNewLastName != null && !userNewLastName.isEmpty()) {
            user.setUserLastName(userNewLastName);
        }

        if(userNewName != null && !userNewName.isEmpty()) {
            user.setUserName(userNewName);
        }

        if(userNewEmail != null && !userNewEmail.isEmpty()) {
            if(UserDAO.isEmailInTable(userNewEmail) == false) {
                user.setUserEmail(userNewEmail);
            } else {
                request.setAttribute("erreur", "Erreur : l'email est déjà utilisée.");
                doGet(request, response);
                return;
            }
        }

        if(userNewBirthdate != null && !userNewBirthdate.isEmpty()) {
            user.setUserBirthdate(userNewBirthdate);
        }

        if(userNewRole != null && !userNewRole.isEmpty()) {
            Role userRole = Role.valueOf(userNewRole);
            if(UserDAO.modifyUserRole(user, user.getUserRole(), userRole) == false) {
                request.setAttribute("erreur", "Erreur lors de la modification du role.");
                doGet(request, response);
                return;
            }
        } else {
            String errorModifyUser = UserDAO.modifyUserFromTable(user);
            if (errorModifyUser != null) {
                request.setAttribute("erreur", errorModifyUser);
                doGet(request, response);
                return;
            }
        }
        // Handle specific modification for students
        if(user.getUserRole().equals(Role.student)) {
            Student student = StudentDAO.getStudentById(user.getUserId());

            if(userNewClassesIdString != null && !userNewClassesIdString.isEmpty()) {
                int userNewClassesId = Integer.parseInt(userNewClassesIdString);
                student.setClassId(userNewClassesId);
            }

            if(userNewMajorIdString != null && !userNewMajorIdString.isEmpty()) {
                int userNewMajorId = Integer.parseInt(userNewMajorIdString);
                student.setMajorId(userNewMajorId);
            }

            String errorModifyStudent = StudentDAO.modifyStudentFromTable(student);
            if(errorModifyStudent != null) {
                request.setAttribute("erreur", errorModifyStudent);
                doGet(request, response);
                return;
            }
        }

        try {
            request.getRequestDispatcher("userManager-servlet?roleFilter="+user.getUserRole()).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
