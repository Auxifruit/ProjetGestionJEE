package com.example.projetjee.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.projetjee.model.entities.Utilisateur;
import com.example.projetjee.model.dao.UtilisateurBDD;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "userServlet", value = "/user-servlet")
public class UserServlet extends HttpServlet {
    private final UtilisateurBDD userBDD = new UtilisateurBDD();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String roleFilter = request.getParameter("roleFilter");
        System.out.println(roleFilter);
        List<Utilisateur> usersList = userBDD.getAllUsers(roleFilter);

        // Set the list of students as a request attribute
        request.setAttribute("users", usersList);

        // Forward the request to result.jsp
        try {
            request.getRequestDispatcher("/roleList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }
}
