package com.example.projetjee.controller;

import com.example.projetjee.model.dao.*;
import com.example.projetjee.model.entities.Classes;
import com.example.projetjee.model.entities.Course;
import com.example.projetjee.model.entities.Grade;
import com.example.projetjee.util.GMailer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "teacherGradeServlet", value = "/teacherGrade-servlet")
public class TeacherGradeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        Integer teacherId = (Integer) session.getAttribute("user");

        if(teacherId == null) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        List<Course> courseList = CourseDAO.getAllCourses();
        List<Grade> teacherGradeList = GradeDAO.getGradeByTeacherId(teacherId);

        Map<Classes, List<Grade>> classGradeMap = new HashMap<>();

        for (Grade grade : teacherGradeList) {
            Classes classe = ClasseDAO.getClasseById(StudentDAO.getStudentById(grade.getStudentId()).getClassId());

            classGradeMap.computeIfAbsent(classe, k -> new ArrayList<>()).add(grade);
        }

        // Construire la map cours -> classGradeMap
        Map<Course, Map<Classes, List<Grade>>> courseClassGradeMap = new HashMap<>();

        for (Course course : courseList) {
            // Filtrer les notes liées à ce cours
            Map<Classes, List<Grade>> filteredClassGradeMap = new HashMap<>();

            for (Map.Entry<Classes, List<Grade>> entry : classGradeMap.entrySet()) {
                Classes classe = entry.getKey();
                List<Grade> grades = entry.getValue();

                // Filtrer les notes de la classe associées au cours
                List<Grade> filteredGrades = new ArrayList<>();
                for (Grade grade : grades) {
                    if (grade.getCourseId() == course.getCourseId()) { // Vérifie si la note appartient au cours
                        filteredGrades.add(grade);
                    }
                }

                if (!filteredGrades.isEmpty()) {
                    filteredClassGradeMap.put(classe, filteredGrades);
                }
            }

            if (!filteredClassGradeMap.isEmpty()) {
                courseClassGradeMap.put(course, filteredClassGradeMap);
            }
        }


        request.setAttribute("courseClassGradeMap", courseClassGradeMap);
        request.getRequestDispatcher("WEB-INF/jsp/pages/teacherGradeViewer.jsp").forward(request, response);
    }
}