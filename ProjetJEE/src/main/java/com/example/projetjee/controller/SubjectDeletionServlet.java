package com.example.projetjee.controller;

import com.example.projetjee.model.dao.SubjectDAO;
import com.example.projetjee.model.entities.Subjects;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "subjectDeletionServlet", value = "/subjectDeletion-servlet")
public class SubjectDeletionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectIdString = request.getParameter("subjectId");

        if(subjectIdString == null || subjectIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une matière.");
            request.getRequestDispatcher("subjectManager-servlet").forward(request, response);
            return;
        }

        int subjectId = Integer.parseInt(subjectIdString);

        if(SubjectDAO.isSubjectInTable(SubjectDAO.getSubjectNameById(subjectId)) == false) {
            request.setAttribute("erreur", "Erreur : La matière n'existe pas.");
            request.getRequestDispatcher("subjectManager-servlet").forward(request, response);
            return;
        }

        if(SubjectDAO.deleteSubjectFromTable(subjectId) == true) {
            request.getRequestDispatcher("subjectManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la suppression de la matière.");
            request.getRequestDispatcher("subjectManager-servlet").forward(request, response);
        }

    }
}
