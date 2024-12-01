package com.example.projetjee.controller;

import com.example.projetjee.util.DatabaseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet implementation for creating a database.
 * This servlet triggers the creation of a new database when accessed.
 */
@WebServlet(name = "CreateDatabaseServlet", value = "/createDatabase-servlet")
public class CreateDatabaseServlet extends HttpServlet {
    /**
     * Handles GET requests by forwarding them to the doPost method.
     * This ensures that the database creation logic is executed
     * even when the request is made via a GET method.
     *
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException if the request cannot be handled
     * @throws IOException if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    /**
     * Handles POST requests to create the database.
     * This method triggers the database creation process using the utility class.
     *
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException if the request cannot be handled
     * @throws IOException if an input or output error occurs
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DatabaseUtil.createDatabase();
    }
}
