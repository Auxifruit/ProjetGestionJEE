package com.example.projetjee.controller;

import com.example.projetjee.model.dao.ClasseDAO;
import com.example.projetjee.model.entities.Classes;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
/**
 * Servlet responsible for handling the modification of classes.
 * This servlet allows the user to view and modify the name of an existing class.
 */
@WebServlet(name = "classesModificationServlet", value = "/classesModification-servlet")
public class ClassesModificationServlet extends HttpServlet {
    /**
     * Handles GET requests to retrieve the class data and forward it to the modification form.
     *
     * @param request  the HttpServletRequest containing the request data
     * @param response the HttpServletResponse to send the response to the client
     * @throws ServletException if an error occurs during servlet processing
     * @throws IOException if an input or output error occurs during the request/response cycle
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String classIdString = request.getParameter("classesId");

        // Check if the class ID is valid
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

    /**
     * Handles POST requests to process the class name modification.
     * It validates the inputs, updates the class name, and saves the changes to the database.
     *
     * @param request  the HttpServletRequest containing the request data
     * @param response the HttpServletResponse to send the response to the client
     * @throws ServletException if an error occurs during servlet processing
     * @throws IOException if an input or output error occurs during the request/response cycle
     */
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

        // Check if the class exists in the database
        if(classe == null) {
            request.setAttribute("erreur", "Erreur : La classe n'existe pas.");
            doGet(request, response);
            return;
        }
        // Check if the new class name is the same as the existing one
        if(classe.getClassName().equals(classesNewName)) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir un nom différent.");
            doGet(request, response);
            return;
        }

        classe.setClassName(classesNewName);
        // Attempt to modify the class in the database
        String error = ClasseDAO.modifyClassFromTable(classe);
        if(error == null) {
            request.getRequestDispatcher("classesManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", error);
            doGet(request, response);
        }

    }


}
