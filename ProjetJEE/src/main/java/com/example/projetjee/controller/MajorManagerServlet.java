package com.example.projetjee.controller;

import com.example.projetjee.model.dao.MajorDAO;
import com.example.projetjee.model.entities.Major;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@WebServlet(name = "majorManagerServlet", value = "/majorManager-servlet")
public class MajorManagerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Major> majorList = MajorDAO.getAllMajor();

        request.setAttribute("majors", majorList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/majorManager.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

}
