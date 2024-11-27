package com.example.projetjee.controller;

import com.example.projetjee.model.dao.*;
import com.example.projetjee.model.entities.*;
import com.example.projetjee.util.DateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "gradeCreationServlet", value = "/gradeCreation-servlet")
public class GradeCreationServlet extends HttpServlet {
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

        if(value < 0 || value > 200) {
            request.setAttribute("erreur", "Erreur : Veuillez saisir une note entre 0 et 20.");
            doGet(request, response);
            return;
        }

        if(coefficient < 0) {
            request.setAttribute("erreur", "Erreur : Veuillez saisir un coefficient supérieur à 0.");
            doGet(request, response);
            return;
        }

        Grade grade = new Grade();
        grade.setGradeName(newGradeName);
        grade.setGradeValue(value);
        grade.setGradeCoefficient(coefficient);
        grade.setStudentId(studentId);
        grade.setCourseId(courseId);
        grade.setTeacherId(teacherId);

        String error = GradeDAO.addGradeInTable(grade);
        if(error == null) {
            request.getRequestDispatcher("gradeManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", error);
            doGet(request, response);
        }
    }


}
