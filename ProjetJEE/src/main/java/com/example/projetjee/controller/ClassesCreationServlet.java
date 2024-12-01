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
/**
 * Servlet responsible for managing class creation functionality.
 * This servlet handles displaying existing classes and creating new classes.
 */
@WebServlet(name = "classesCreationServlet", value = "/classesCreation-servlet")
public class ClassesCreationServlet extends HttpServlet {
    /**
     * Handles GET requests to display the list of existing classes.
     * Retrieves all classes from the database and forwards to the creation page.
     *
     * @param request  the HttpServletRequest containing the request data
     * @param response the HttpServletResponse to send the response to the client
     * @throws IOException if an input or output exception occurs
     * @throws ServletException if the request cannot be processed
     */
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
    /**
     * Handles POST requests to create a new class.
     * It validates the class name input and adds the new class to the database.
     *
     * @param request  the HttpServletRequest containing the request data
     * @param response the HttpServletResponse to send the response to the client
     * @throws ServletException if the request cannot be processed
     * @throws IOException if an input or output exception occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String classesName = request.getParameter("newClasses");

        if(classesName == null || classesName.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez saisir le nom de la nouvelle classe.");
            doGet(request, response);
            return;
        }

        // Create a new Classes object and set its name
        Classes classe = new Classes();
        classe.setClassName(classesName);

        // Attempt to add the new class to the database
        String error = ClasseDAO.addClasseInTable(classe);
        // If the class is successfully added, redirect to the classes manager servlet
        if(error == null) {
            request.getRequestDispatcher("classesManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", error);
            doGet(request, response);
        }

    }


}
