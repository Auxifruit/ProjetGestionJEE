package com.example.projetjee.controller;

import com.example.projetjee.model.dao.*;
import com.example.projetjee.model.entities.*;
import com.example.projetjee.util.GMailer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet to handle the creation of grades for students.
 * It displays the grade creation form and handles the form submission to create a new grade entry.
 */
@WebServlet(name = "gradeCreationServlet", value = "/gradeCreation-servlet")
public class GradeCreationServlet extends HttpServlet {

    /**
     * Handles the GET request to display the grade creation form.
     * Retrieves the list of grades, courses, students, and teachers and sets them as request attributes.
     *
     * @param request the HttpServletRequest containing the request data
     * @param response the HttpServletResponse to send the response
     * @throws IOException if an input or output error occurs
     * @throws ServletException if the servlet encounters a problem
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Grade> gradeList = GradeDAO.getAllGrade();
        List<Course> courseList = CourseDAO.getAllCourses();
        List<Student> studentList = StudentDAO.getAllStudent();
        List<Teacher> teacherList = TeacherDAO.getAllTeacher();

        request.setAttribute("grades", gradeList);
        request.setAttribute("courses", courseList);
        request.setAttribute("students", studentList);
        request.setAttribute("teachers", teacherList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/gradeCreation.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the POST request to create a new grade.
     * Validates the input data and adds the grade to the database. Sends an email to the student after the grade is created.
     *
     * @param request the HttpServletRequest containing the request data
     * @param response the HttpServletResponse to send the response
     * @throws ServletException if the servlet encounters a problem
     * @throws IOException if an input or output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newGradeName = request.getParameter("newGradeName");
        String newGradeCourseIdString = request.getParameter("newGradeCourseId");
        String newGradeValueString = request.getParameter("newGradeValue");
        String newGradeCoefficientString = request.getParameter("newGradeCoefficient");
        String newGradeStudentIdString = request.getParameter("newGradeStudentId");
        String newGradeTeacherIdString = request.getParameter("newGradeTeacherId");

        if((newGradeName == null || newGradeName.isEmpty()) || (newGradeCourseIdString == null || newGradeCourseIdString.isEmpty()) || (newGradeValueString == null || newGradeValueString.isEmpty())
                || (newGradeCoefficientString == null || newGradeCoefficientString.isEmpty()) || (newGradeStudentIdString == null || newGradeStudentIdString.isEmpty()) || (newGradeTeacherIdString == null || newGradeTeacherIdString.isEmpty())) {
            request.setAttribute("erreur", "Erreur : Veuillez remplir tous les champs.");
            doGet(request, response);
            return;
        }

        double value = Double.parseDouble(newGradeValueString);
        int coefficient = Integer.parseInt(newGradeCoefficientString);
        int studentId = Integer.parseInt(newGradeStudentIdString);
        int courseId = Integer.parseInt(newGradeCourseIdString);
        int teacherId = Integer.parseInt(newGradeTeacherIdString);

        // Additional validation for value and coefficient
        if(value < 0 || value > 20) {
            request.setAttribute("erreur", "Erreur : Veuillez saisir une note entre 0 et 20.");
            doGet(request, response);
            return;
        }

        if(coefficient < 0) {
            request.setAttribute("erreur", "Erreur : Veuillez saisir un coefficient supérieur à 0.");
            doGet(request, response);
            return;
        }

        // Create grade object
        Grade grade = new Grade();
        grade.setGradeName(newGradeName);
        grade.setGradeValue(value);
        grade.setGradeCoefficient(coefficient);
        grade.setStudentId(studentId);
        grade.setCourseId(courseId);
        grade.setTeacherId(teacherId);

        // Add grade to the database
        String error = GradeDAO.addGradeInTable(grade);
        if(error == null) {
            String studentEmail = UserDAO.getUserEmailById(studentId);
            if (studentEmail != null) {
                // Email preparation
                String subject = "Nouvelle note reçue";
                String body = "Bonjour,\n\n"
                    + "Vous avez reçu une nouvelle note pour le cours : " + grade.getGradeName() + ".\n"
                    + "Note : " + grade.getGradeValue() + "/20\n"
                    + "Coefficient : " + grade.getGradeCoefficient() + "\n\n"
                    + "Cordialement,\nL'équipe pédagogique";

                // Email sending procedure
                try {
                    GMailer gmailer = new GMailer();  // Instantiate GMailer
                    gmailer.sendMail(subject, body, studentEmail);  // Send the email
                    request.getRequestDispatcher("gradeManager-servlet").forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("erreur", "Erreur lors de l'envoi de l'email.");
                    request.getRequestDispatcher("gradeManager-servlet").forward(request, response);
                }
            } else {
                request.setAttribute("erreur", "Utilisateur non trouvé pour l'étudiant.");
                doGet(request, response);
            }
        } else {
            request.setAttribute("erreur", error);
            doGet(request, response);
        }
    }


}
