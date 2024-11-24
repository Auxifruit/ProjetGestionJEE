package com.example.projetjee.controller;

import com.example.projetjee.model.dao.SubjectDAO;
import com.example.projetjee.model.entities.Subjects;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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
        Subjects subject = SubjectDAO.getSubjectById(subjectId);

        try {
            request.setAttribute("subject", subject);
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

        if(SubjectDAO.getSubjectById(subjectId) == null) {
            request.setAttribute("erreur", "Erreur : La matière n'existe pas.");
            doGet(request, response);
            return;
        }

        Subjects subject = SubjectDAO.getSubjectById(subjectId);

        if(subject.getSubjectName().equals(subjectNewName)) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un nouveau différent.");
            doGet(request, response);
            return;
        }

        subject.setSubjectName(subjectNewName);

        String error = SubjectDAO.modifySubjectFromTable(subject);
        if(error == null) {
            request.getRequestDispatcher("subjectManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", error);
            doGet(request, response);
        }

    }


}
