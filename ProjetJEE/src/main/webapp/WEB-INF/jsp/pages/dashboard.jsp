<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.entities.Users" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Tableau de bord</title>
</head>
<body>
<%
    int userId = (int) request.getSession().getAttribute("user");
    Users user = UserDAO.getUserById(userId);
%>
<h1>Bienvenue, <%= user.getUserName() + " " + user.getUserLastName() %> !</h1>
<p>Vous êtes connecté avec succès.</p>
<a href="index.jsp"> <button>Retour index</button></a>
</body>
</html>
