package com.example.projetjee.controller;

import com.example.projetjee.model.dao.ClasseDAO;
import com.example.projetjee.model.entities.Classes;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "classesModificationServlet", value = "/classesModification-servlet")
public class ClassesModificationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String classIdString = request.getParameter("classesId");

        if(classIdString == null || classIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une classe.");
            request.getRequestDispatcher("classesManager-servlet").forward(request, response);
            return;
        }

        int classId = Integer.parseInt(classIdString);
        Classes classe = ClasseDAO.getClasseById(classId);

        try {
            request.setAttribute("classe", classe);
            request.getRequestDispatcher("WEB-INF/jsp/pages/classesModification.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String classesIdString = request.getParameter("classesId");
        String classesNewName = request.getParameter("classesNewName");

        if(classesIdString == null || classesIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une classe.");
            doGet(request, response);
            return;
        }

        if(classesNewName == null || classesNewName.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un nouveau nom.");
            doGet(request, response);
            return;
        }

        int classesId = Integer.parseInt(classesIdString);
        Classes classe = ClasseDAO.getClasseById(classesId);

        if(classe == null) {
            request.setAttribute("erreur", "Erreur : La classe n'existe pas.");
            doGet(request, response);
            return;
        }

        if(classe.getClassName().equals(classesNewName)) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un nom différent.");
            doGet(request, response);
            return;
        }

        classe.setClassName(classesNewName);

        if(ClasseDAO.modifyClassFromTable(classe) == true) {
            request.getRequestDispatcher("classesManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la modification de la classe.");
            doGet(request, response);
        }

    }


}
