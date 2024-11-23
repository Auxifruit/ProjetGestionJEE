package com.example.projetjee.controller;

import com.example.projetjee.model.dao.MajorDAO;
import com.example.projetjee.model.entities.Major;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "majorCreationServlet", value = "/majorCreation-servlet")
public class MajorCreationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Major> majorList = MajorDAO.getAllMajor();

        request.setAttribute("majors", majorList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/majorCreation.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String majorName = request.getParameter("newMajor");

        if(majorName == null || majorName.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez saisir le nom de la nouvelle filière.");
            doGet(request, response);
            return;
        }

        /*
        // A MODIFIER
        if(MajorDAO.isMajorInTable(majorName) == true) {
            request.setAttribute("erreur", "Erreur : La filière existe déjà.");
            doGet(request, response);
            return;
        }
        */

        Major major = new Major();
        major.setMajorName(majorName);

        if(MajorDAO.addMajorInTable(major) == true) {
            request.getRequestDispatcher("majorManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de l'ajout de la filière.");
            doGet(request, response);
        }

    }


}
