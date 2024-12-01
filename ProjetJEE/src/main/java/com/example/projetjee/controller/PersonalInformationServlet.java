package com.example.projetjee.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PersonalInformationServlet.
 * This servlet handles requests related to personal information. It processes both GET and POST requests
 * by forwarding the request to the JSP page for personal information.
 */
@WebServlet(name = "personalInformationServlet", value = "/personalInformation-servlet")

public class PersonalInformationServlet extends HttpServlet {
    /**
     * Handles GET requests. It delegates the processing to the doPost method.
     *
     * @param request  the HttpServletRequest object that contains the request from the client
     * @param response the HttpServletResponse object that contains the response to be sent to the client
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }

    /**
     * Handles POST requests by forwarding the request to the JSP page for personal information.
     *
     * @param request  the HttpServletRequest object that contains the request from the client
     * @param response the HttpServletResponse object that contains the response to be sent to the client
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("WEB-INF/jsp/pages/personalInformation.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
