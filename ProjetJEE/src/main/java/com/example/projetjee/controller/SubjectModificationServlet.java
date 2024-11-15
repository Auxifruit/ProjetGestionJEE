package com.example.projetjee.controller;

import com.example.projetjee.model.dao.SubjectDAO;
import com.example.projetjee.model.entities.Matiere;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "subjectModificationServlet", value = "/subjectModification-servlet")
public class SubjectModificationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectIdString = request.getParameter("subjectId");

        if(subjectIdString == null || subjectIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une matière.");
            request.getRequestDispatcher("subjectManager-servlet").forward(request, response);
            return;
        }

        int subjectId = Integer.parseInt(subjectIdString);

        try {
            request.setAttribute("subjectId", subjectId);
            request.setAttribute("subjectName", SubjectDAO.getSubjectNameById(subjectId));
            request.getRequestDispatcher("WEB-INF/jsp/pages/subjectModification.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectIdString = request.getParameter("subjectId");
        String subjectNewName = request.getParameter("subjectNewName");

        if(subjectIdString == null || subjectIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une matière.");
            doGet(request, response);
            return;
        }

        if(subjectNewName == null || subjectNewName.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un nouveau nom.");
            doGet(request, response);
            return;
        }

        int subjectId = Integer.parseInt(subjectIdString);

        if(SubjectDAO.isSubjectInTable(SubjectDAO.getSubjectNameById(subjectId)) == false) {
            request.setAttribute("erreur", "Erreur : La matière n'existe pas.");
            doGet(request, response);
            return;
        }

        Matiere subject = SubjectDAO.getSubject(subjectId);

        if(subject.getNomMatiere().equals(subjectNewName)) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un nouveau différent.");
            doGet(request, response);
            return;
        }

        if(SubjectDAO.modifySubjectFromTable(subjectId, subjectNewName) == true) {
            request.getRequestDispatcher("subjectManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la suppression de la matière.");
            doGet(request, response);
        }

    }


}
