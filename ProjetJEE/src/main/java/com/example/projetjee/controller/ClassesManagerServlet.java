package com.example.projetjee.controller;

import com.example.projetjee.model.dao.ClasseDAO;
import com.example.projetjee.model.entities.Classes;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * Servlet that manages classes.
 * This servlet retrieves and displays all the classes in the system.
 */
@WebServlet(name = "classesManagerServlet", value = "/classesManager-servlet")
public class ClassesManagerServlet extends HttpServlet {
    /**
     * Handles GET requests to retrieve and display all the classes.
     * It fetches the list of classes from the database and forwards it to the JSP page.
     *
     * @param request  the HttpServletRequest containing the request data
     * @param response the HttpServletResponse to send the response to the client
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Classes> classesList = ClasseDAO.getAllClasses();

        request.setAttribute("classes", classesList);

        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/classesManager.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Handles POST requests. This method delegates to the GET method to retrieve the list of classes.
     * The POST request does not modify any data but will perform the same action as the GET request.
     *
     * @param request  the HttpServletRequest containing the request data
     * @param response the HttpServletResponse to send the response to the client
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

}
