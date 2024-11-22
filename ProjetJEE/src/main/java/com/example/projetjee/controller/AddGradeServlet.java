package com.example.projetjee.controller;

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

import static com.example.projetjee.model.dao.GradeDAO.insertGrade;

@WebServlet(name = "AddGradeServlet", value = "/add-grade-servlet")
public class AddGradeServlet extends HttpServlet {
    private int teacherId = 8;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // get the data from the form
        Map<String, String[]> parameterMap = request.getParameterMap();
        // create a list of grade for an easy manipulation
        List<Grade> grades = new ArrayList<>();

        try {
            // global parameter
            String courseIdParam = request.getParameter("courseId");
            String gradeName = request.getParameter("gradeName");
            String gradeCoefficientParam = request.getParameter("gradeCoefficient");

            if (courseIdParam == null || gradeName == null || gradeCoefficientParam == null) {
                throw new IllegalArgumentException("Les paramètres (courseId, gradeName, gradeCoefficient) n'existe pas.");
            }

            int courseId = Integer.parseInt(courseIdParam);
            int gradeCoefficient = Integer.parseInt(gradeCoefficientParam);

            // create each grade element
            for (String paramName : parameterMap.keySet()) {
                if (paramName.startsWith("grade_")) {
                    try {
                        int studentId = Integer.parseInt(paramName.split("_")[1]);
                        double gradeValue = Double.parseDouble(parameterMap.get(paramName)[0]);

                        // the object
                        Grade grade = new Grade();
                        grade.setGradeName(gradeName);
                        grade.setGradeValue(gradeValue);
                        grade.setGradeCoefficient(gradeCoefficient);
                        grade.setStudentId(studentId);
                        grade.setCourseId(courseId);
                        grade.setTeacherId(teacherId); // need to be applied to the session user

                        grades.add(grade);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        System.out.println("Erreur de format pour les notes ou les IDs des étudiants.");
                    }
                }
            }

            // insert grades in the database
            for (Grade grade : grades) {
                if (!insertGrade(grade)) {
                    System.out.println("Erreur d'insertion de la note de l'étudiant ID: " + grade.getStudentId());
                }
            }

            request.getRequestDispatcher("/WEB-INF/jsp/pages/addGradeDone.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "error_insert_grade" + e.getMessage());
        }
    }
}
