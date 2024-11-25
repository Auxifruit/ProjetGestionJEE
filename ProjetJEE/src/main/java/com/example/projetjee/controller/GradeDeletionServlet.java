package com.example.projetjee.controller;

import com.example.projetjee.model.dao.GradeDAO;
import com.example.projetjee.model.dao.SubjectDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "gradeDeletionServlet", value = "/gradeDeletion-servlet")
public class GradeDeletionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String gradeIdString = request.getParameter("gradeId");

        if(gradeIdString == null || gradeIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une note.");
            request.getRequestDispatcher("gradeManager-servlet").forward(request, response);
            return;
        }

        int gradeId = Integer.parseInt(gradeIdString);

        if(GradeDAO.getGradeById(gradeId) == null) {
            request.setAttribute("erreur", "Erreur : La note n'existe pas.");
            request.getRequestDispatcher("gradeManager-servlet").forward(request, response);
            return;
        }

        if(GradeDAO.deleteGradeFromTable(gradeId) == true) {
            request.getRequestDispatcher("gradeManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la suppression de la note.");
            request.getRequestDispatcher("gradeManager-servlet").forward(request, response);
        }

    }
}
