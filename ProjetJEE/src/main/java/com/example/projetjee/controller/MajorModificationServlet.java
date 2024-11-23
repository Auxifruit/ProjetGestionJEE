package com.example.projetjee.controller;

import com.example.projetjee.model.dao.MajorDAO;
import com.example.projetjee.model.entities.Major;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "majorModificationServlet", value = "/majorModification-servlet")
public class MajorModificationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String majorIdString = request.getParameter("majorId");

        if(majorIdString == null || majorIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une filière.");
            request.getRequestDispatcher("majorManager-servlet").forward(request, response);
            return;
        }

        int majorIdId = Integer.parseInt(majorIdString);
        Major major = MajorDAO.getMajorById(majorIdId);

        try {
            request.setAttribute("major", major);
            request.getRequestDispatcher("WEB-INF/jsp/pages/majorModification.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String majorIdString = request.getParameter("majorId");
        String majorNewName = request.getParameter("majorNewName");

        if(majorIdString == null || majorIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une filière.");
            doGet(request, response);
            return;
        }

        if(majorNewName == null || majorNewName.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un nouveau nom.");
            doGet(request, response);
            return;
        }

        int majorId = Integer.parseInt(majorIdString);
        Major major = MajorDAO.getMajorById(majorId);

        if(major == null) {
            request.setAttribute("erreur", "Erreur : La filière n'existe pas.");
            doGet(request, response);
            return;
        }

        if(major.getMajorName().equals(majorNewName)) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un nom différent.");
            doGet(request, response);
            return;
        }

        major.setMajorName(majorNewName);

        if(MajorDAO.modifyMajorFromTable(major) == true) {
            request.getRequestDispatcher("majorManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la modification de la filière.");
            doGet(request, response);
        }

    }


}
