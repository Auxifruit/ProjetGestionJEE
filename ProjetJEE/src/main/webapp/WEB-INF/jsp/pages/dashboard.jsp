<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Tableau de bord</title>
</head>
<body>
<%
    int userId = (int) request.getSession().getAttribute("user");
%>
<h1>Bienvenue, <%= UserDAO.getNameById(userId) + " " + UserDAO.getLastNameById(userId) %> !</h1>
<p>Vous êtes connecté avec succès.</p>
<a href="index.jsp"> <button>Retour index</button></a>
</body>
</html>
