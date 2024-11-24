package com.example.projetjee.controller;

import com.example.projetjee.model.dao.ClasseDAO;
import com.example.projetjee.model.entities.Classes;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "classesCreationServlet", value = "/classesCreation-servlet")
public class ClassesCreationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Classes> classesList = ClasseDAO.getAllClasses();

        request.setAttribute("classes", classesList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/classesCreation.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String classesName = request.getParameter("newClasses");

        if(classesName == null || classesName.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez saisir le nom de la nouvelle classe.");
            doGet(request, response);
            return;
        }

        /*
        // A MODIFIER
        if(ClasseDAO.isClassesInTable(classesName) == true) {
            request.setAttribute("erreur", "Erreur : La classe existe déjà.");
            doGet(request, response);
            return;
        }
        */

        Classes classe = new Classes();
        classe.setClassName(classesName);

        String error = ClasseDAO.addClasseInTable(classe);
        if(error == null) {
            request.getRequestDispatcher("classesManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", error);
            doGet(request, response);
        }

    }


}
