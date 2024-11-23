package com.example.projetjee.controller;

import com.example.projetjee.model.dao.ClasseDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "classesDeletionServlet", value = "/classesDeletion-servlet")
public class ClassesDeletionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String classIdString = request.getParameter("classesId");

        if(classIdString == null || classIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une classe.");
            request.getRequestDispatcher("majorManager-servlet").forward(request, response);
            return;
        }

        int classId = Integer.parseInt(classIdString);

        if(ClasseDAO.getClasseById(classId) == null) {
            request.setAttribute("erreur", "Erreur : La filière n'existe pas.");
            request.getRequestDispatcher("classesManager-servlet").forward(request, response);
            return;
        }

        if(ClasseDAO.deleteClasseFromTable(classId) == true) {
            request.getRequestDispatcher("classesManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la suppression de la classe.");
            request.getRequestDispatcher("classesManager-servlet").forward(request, response);
        }

    }
}
