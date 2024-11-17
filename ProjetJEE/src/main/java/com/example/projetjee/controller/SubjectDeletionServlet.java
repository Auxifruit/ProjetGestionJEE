package com.example.projetjee.controller;

import com.example.projetjee.model.dao.SubjectDAO;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Matiere> subjectList = SubjectDAO.getAllSubject();

        request.setAttribute("subjects", subjectList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/subjectDeletion.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectIdString = request.getParameter("subjectIdToDelete");

        if(subjectIdString == null || subjectIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une matière.");
            doGet(request, response);
            return;
        }

        int subjectId = Integer.parseInt(subjectIdString);

        if(SubjectDAO.isSubjectInTable(SubjectDAO.getSubjectNameById(subjectId)) == false) {
            request.setAttribute("erreur", "Erreur : La matière n'existe pas.");
            doGet(request, response);
            return;
        }

        if(SubjectDAO.deleteSubjectFromTable(subjectId) == true) {
            doGet(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la suppression de la matière.");
            doGet(request, response);
        }

    }


}
