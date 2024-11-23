package com.example.projetjee.controller;

import java.io.*;
import java.util.List;
import java.util.Map;

import com.example.projetjee.model.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServletException {
        // Récupérer les paramètres de recherche
        String filterBy = request.getParameter("filterBy");
        String searchValue = request.getParameter("searchValue");
        String selectedClass = request.getParameter("selectedClass"); // Nom du paramètre pour la classe

        // Liste initiale des utilisateurs
        List<Map<String, Object>> users = UserDAO.getAllUsersWithClasses(); // Supposons que cette méthode retourne la liste des utilisateurs avec leurs classes
        final List<String> availableClasses = UserDAO.getAllClassNames(); // Supposons que cette méthode retourne la liste des noms de classes disponibles





        // Ajouter les données dans les attributs de requête
        request.setAttribute("users", users);
        request.setAttribute("availableClasses", availableClasses); // Pour peupler le menu déroulant
        request.setAttribute("filterBy", filterBy); // Pour conserver le filtre actuel
        request.setAttribute("searchValue", searchValue); // Pour conserver la valeur de recherche actuelle
        request.setAttribute("selectedClass", selectedClass); // Pour conserver la classe sélectionnée

        System.out.println("Paramètres : filterBy=" + filterBy + ", searchValue=" + searchValue);
        System.out.println("Avant filtrage : " + users.size() + " utilisateurs");
        System.out.println("Utilisateurs après filtrage : " + users);
        System.out.println("SelectedClass : " + selectedClass);
        System.out.println("AvailableClasses : " + availableClasses);

        // Redirection vers la page JSP
        request.getRequestDispatcher("WEB-INF/jsp/pages/ListStudents.jsp").forward(request, response);
    }

}