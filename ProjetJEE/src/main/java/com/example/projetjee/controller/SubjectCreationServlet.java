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

@WebServlet(name = "subjectCreationServlet", value = "/subjectCreation-servlet")
public class SubjectCreationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Subjects> subjectList = SubjectDAO.getAllSubjects();

        request.setAttribute("subjects", subjectList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/subjectCreation.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectName = request.getParameter("newSubject");

        if(subjectName == null || subjectName.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez saisir le nom de la nouvelle matière.");
            doGet(request, response);
            return;
        }

        Subjects subject = new Subjects();
        subject.setSubjectName(subjectName);

        String error = SubjectDAO.addSubjectInTable(subject);
        if(error == null) {
            request.getRequestDispatcher("subjectManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", error);
            doGet(request, response);
        }

    }


}
