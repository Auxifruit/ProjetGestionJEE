package com.example.projetjee.controller;

import com.example.projetjee.model.dao.ClasseDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet responsible for handling class deletion requests.
 * This servlet deletes a class from the system based on the class ID provided.
 */
@WebServlet(name = "classesDeletionServlet", value = "/classesDeletion-servlet")
public class ClassesDeletionServlet extends HttpServlet {

    /**
     * Handles GET requests for class deletion.
     * This method redirects the GET request to the POST method for processing.
     *
     * @param request  the HttpServletRequest containing the request data
     * @param response the HttpServletResponse to send the response to the client
     * @throws ServletException if the request cannot be processed
     * @throws IOException if an input or output exception occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    /**
     * Handles POST requests to delete a class.
     * It validates the class ID, checks if the class exists, and performs the deletion.
     *
     * @param request  the HttpServletRequest containing the request data
     * @param response the HttpServletResponse to send the response to the client
     * @throws ServletException if the request cannot be processed
     * @throws IOException if an input or output exception occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String classIdString = request.getParameter("classesId");

        if(classIdString == null || classIdString.isEmpty()) {
            request.setAttribute("erreur", "Erreur : Veuillez choisir une classe.");
            request.getRequestDispatcher("classesManager-servlet").forward(request, response);
            return;
        }

        int classId = Integer.parseInt(classIdString);

        // Check if the class exists in the database
        if(ClasseDAO.getClasseById(classId) == null) {
            request.setAttribute("erreur", "Erreur : La filière n'existe pas.");
            request.getRequestDispatcher("classesManager-servlet").forward(request, response);
            return;
        }

        // Attempt to delete the class from the database
        if(ClasseDAO.deleteClasseFromTable(classId) == true) {
            request.getRequestDispatcher("classesManager-servlet").forward(request, response);
        }
        else {
            request.setAttribute("erreur", "Erreur : Erreur lors de la suppression de la classe.");
            request.getRequestDispatcher("classesManager-servlet").forward(request, response);
        }

    }
}
