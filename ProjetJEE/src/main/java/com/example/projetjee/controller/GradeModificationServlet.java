package com.example.projetjee.controller;

import com.example.projetjee.model.dao.CourseDAO;
import com.example.projetjee.model.dao.GradeDAO;
import com.example.projetjee.model.dao.StudentDAO;
import com.example.projetjee.model.dao.TeacherDAO;
import com.example.projetjee.model.entities.Course;
import com.example.projetjee.model.entities.Grade;
import com.example.projetjee.model.entities.Student;
import com.example.projetjee.model.entities.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class GradeModificationServlet.
 * This servlet handles the modification of grades in the system.
 * It allows the user to modify various aspects of a grade, such as its name, value, associated course, coefficient,
 * student, and teacher.
 */
@WebServlet(name = "gradeModificationServlet", value = "/gradeModification-servlet")
public class GradeModificationServlet extends HttpServlet {

    /**
     * Handles the GET request to retrieve information for modifying a grade.
     * This method retrieves the grade by its ID, as well as lists of courses, students, and teachers
     * to display them in the grade modification form.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws IOException      if an input or output error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String gradeIdString = request.getParameter("gradeId");
        List<Course> courseList = CourseDAO.getAllCourses();
        List<Student> studentList = StudentDAO.getAllStudent();
        List<Teacher> teacherList = TeacherDAO.getAllTeacher();

        // If gradeId is null or empty, redirect with an error
        if(gradeIdString == null || gradeIdString.isEmpty()) {
            request.setAttribute("error", "Erreur : Veuillez choisir une note.");
            request.getRequestDispatcher("gradeManager-servlet").forward(request, response);
            return;
        }

        int gradeId = Integer.parseInt(gradeIdString);

        // Set the grade and lists as request attributes for the JSP
        request.setAttribute("grade", GradeDAO.getGradeById(gradeId));
        request.setAttribute("courses", courseList);
        request.setAttribute("students", studentList);
        request.setAttribute("teachers", teacherList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/gradeModification.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the POST request to modify a grade.
     * This method processes the updated information for the grade and saves the changes to the database.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String gradeIdString = request.getParameter("gradeId");
        String gradeNewName = request.getParameter("gradeNewName");
        String gradeNewCourseIdString = request.getParameter("gradeNewCourseId");
        String gradeNewValueString = request.getParameter("gradeNewValue");
        String gradeNewCoefficientString = request.getParameter("gradeNewCoefficient");
        String gradeNewStudentIdString = request.getParameter("gradeNewStudentId");
        String gradeNewTeacherId = request.getParameter("gradeNewTeacherId");

        if(gradeIdString == null || gradeIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une note.");
            request.getRequestDispatcher("gradeManager-servlet").forward(request, response);
            return;
        }
        // Ensure at least one field is provided for modification
        if((gradeNewName == null || gradeNewName.isEmpty()) && (gradeNewCourseIdString == null || gradeNewCourseIdString.isEmpty()) && (gradeNewValueString == null || gradeNewValueString.isEmpty())
                && (gradeNewCoefficientString == null || gradeNewCoefficientString.isEmpty()) && (gradeNewStudentIdString == null || gradeNewStudentIdString.isEmpty()) && (gradeNewTeacherId == null || gradeNewTeacherId.isEmpty())) {
            request.setAttribute("erreur", "Erreur : Veuillez remplir au moins un champs.");
            doGet(request, response);
            return;
        }

        int gradeId = Integer.parseInt(gradeIdString);

        Grade grade = GradeDAO.getGradeById(gradeId);

        if(gradeNewName != null && !gradeNewName.isEmpty()) {
            grade.setGradeName(gradeNewName);
        }

        if(gradeNewValueString != null && !gradeNewValueString.isEmpty()) {
            double value = Double.parseDouble(gradeNewValueString);

            // Validate the grade value
            if(value < 0 || value > 20) {
                request.setAttribute("erreur", "Erreur : Veuillez saisir une note entre 0 et 20.");
                doGet(request, response);
                return;
            }

            grade.setGradeValue(value);
        }

        if(gradeNewCourseIdString != null && !gradeNewCourseIdString.isEmpty()) {
            int courseId = Integer.parseInt(gradeNewCourseIdString);
            grade.setCourseId(courseId);
        }

        if(gradeNewCoefficientString != null && !gradeNewCoefficientString.isEmpty()) {
            int coefficient = Integer.parseInt(gradeNewCoefficientString);

            // Validate the coefficient
            if(coefficient < 0) {
                request.setAttribute("erreur", "Erreur : Veuillez saisir un coefficient supérieur à 0.");
                doGet(request, response);
                return;
            }

            grade.setGradeCoefficient(coefficient);
        }

        if(gradeNewStudentIdString != null && !gradeNewStudentIdString.isEmpty()) {
            int studentId = Integer.parseInt(gradeNewStudentIdString);

            grade.setStudentId(studentId);
        }

        if(gradeNewTeacherId != null && !gradeNewTeacherId.isEmpty()) {
            int teacherId = Integer.parseInt(gradeNewTeacherId);

            grade.setTeacherId(teacherId);
        }

        // Update the grade in the database
        String error = GradeDAO.modifyGradeFromTable(grade);
        if(error == null) {
            request.getRequestDispatcher("gradeManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", error);
            doGet(request, response);
        }
    }


}
