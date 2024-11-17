package com.example.projetjee.controller;

import java.io.*;
import java.util.List;

import com.example.projetjee.model.entities.Student;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.example.projetjee.model.dao.StudentDAO;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private final StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Student> students = studentDAO.getAllStudents();

        // Set the list of students as a request attribute
        request.setAttribute("students", students);

        // Forward the request to result.jsp
        try {
            request.getRequestDispatcher("/result.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }
}