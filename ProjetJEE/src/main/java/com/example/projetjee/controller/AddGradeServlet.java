package com.example.projetjee.controller;

import com.example.projetjee.model.dao.GradeDAO;
import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.util.GMailer;
import com.example.projetjee.model.entities.Grade;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Servlet responsible for adding grades for students.
 * It processes the grade data submitted from the form, adds grades to the database,
 * and sends an email notification to students about their new grade.
 */
@WebServlet(name = "AddGradeServlet", value = "/add-grade-servlet")
public class AddGradeServlet extends HttpServlet {

    /**
     * Handles the POST request to add grades for students.
     * It retrieves grade data from the form, creates grade objects,
     * saves them to the database, and sends email notifications.
     *
     * @param request  the HttpServletRequest containing the data from the client
     * @param response the HttpServletResponse to send back to the client
     * @throws IOException if an input or output exception occurs
     * @throws ServletException if the request cannot be handled
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int teacherId = Integer.parseInt(request.getParameter("teacherID"));
        // get the data from the form
        Map<String, String[]> parameterMap = request.getParameterMap();
        // create a list of grade for easy manipulation
        List<Grade> grades = new ArrayList<>();

        GMailer gMailer = null;

        try {
            gMailer = new GMailer();
            // global parameters
            String courseIdParam = request.getParameter("courseId");
            String gradeName = request.getParameter("gradeName");
            String gradeCoefficientParam = request.getParameter("gradeCoefficient");

            if (courseIdParam == null || gradeName == null || gradeCoefficientParam == null) {
                throw new IllegalArgumentException("Les paramètres (courseId, gradeName, gradeCoefficient) n'existent pas.");
            }

            int courseId = Integer.parseInt(courseIdParam);
            int gradeCoefficient = Integer.parseInt(gradeCoefficientParam);

            // create each grade element
            for (String paramName : parameterMap.keySet()) {
                if (paramName.startsWith("grade_")) {
                    try {
                        int studentId = Integer.parseInt(paramName.split("_")[1]);
                        double gradeValue = Double.parseDouble(parameterMap.get(paramName)[0]);

                        // create the grade object
                        Grade grade = new Grade();
                        grade.setGradeName(gradeName);
                        grade.setGradeValue(gradeValue);
                        grade.setGradeCoefficient(gradeCoefficient);
                        grade.setStudentId(studentId);
                        grade.setCourseId(courseId);
                        grade.setTeacherId(teacherId); // teacher's ID

                        grades.add(grade);  // Add the grade to the list
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }

            // insert grades in the database and send emails
            for (Grade grade : grades) {
                if (GradeDAO.addGradeInTable(grade) != null) {
                    // Get the student's email
                    String studentEmail = UserDAO.getUserEmailById(grade.getStudentId());

                    // Send email to the student
                    String subject = "Nouvelle Note ajoutée";
                    String message = "Bonjour,\n\nUne nouvelle note a été ajoutée à votre dossier pour le cours " +
                            grade.getGradeName() + ".\nNote : " + grade.getGradeValue() + "\nCordialement,\nL'équipe pédagogique.";

                    // Sending the email
                    gMailer.sendMail(subject, message, studentEmail);
                } else {
                    System.out.println("Erreur d'insertion de la note pour l'étudiant ID: " + grade.getStudentId());
                }
            }

            // set back the ID for another use
            request.setAttribute("teacherID", teacherId);
            // forward to a success page
            request.getRequestDispatcher("/WEB-INF/jsp/pages/addGradeDone.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "error_insert_grade" + e.getMessage());
        }
    }
}
