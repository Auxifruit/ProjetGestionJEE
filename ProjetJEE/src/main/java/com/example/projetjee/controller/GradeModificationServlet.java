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

@WebServlet(name = "gradeModificationServlet", value = "/gradeModification-servlet")
public class GradeModificationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String gradeIdString = request.getParameter("gradeId");
        List<Course> courseList = CourseDAO.getAllCourses();
        List<Student> studentList = StudentDAO.getAllStudent();
        List<Teacher> teacherList = TeacherDAO.getAllTeacher();

        if(gradeIdString == null || gradeIdString.isEmpty()) {
            request.setAttribute("error", "Erreur : Veuillez choisir une note.");
            request.getRequestDispatcher("gradeManager-servlet").forward(request, response);
            return;
        }

        int gradeId = Integer.parseInt(gradeIdString);


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

            if(value < 0 || value > 200) {
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
