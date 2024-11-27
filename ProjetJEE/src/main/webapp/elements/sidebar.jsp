<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.entities.Role" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
</head>
<style>
    body {
        margin-left: 350px;
    }

    .sidebar {
        position: fixed;
        width: 250px;
        height: 100%;
        background-color: #77A4C6;
        color: white;
        padding: 20px;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
        top: 0;
        left: 0;
        z-index: 1000;
        border-top-right-radius: 10px;
        border-bottom-right-radius: 10px;
    }

    .sidebar h2 {
        text-align: center;
        font-size: 24px;
        margin-bottom: 20px;
        border-bottom: 1px solid white;
        padding-bottom: 10px;
    }

    .sidebar ul {
        list-style: none;
        padding: 0;
    }

    .sidebar ul li {
        margin: 15px 0;
    }

    .sidebar ul li a {
        color: white;
        text-decoration: none;
        font-size: 16px;
        padding: 10px;
        display: block;
        border-radius: 8px;
        transition: background-color 0.3s ease;
    }

    .sidebar ul li a:hover {
        background-color: #A6BBD6;
    }
</style>
<body>
<div class="sidebar">
    <a href="index.jsp"><img src="${pageContext.request.contextPath}/css/logo.png" alt="logo" style="width:120px;height:120px;"></a>
    <%
        Integer userId = (Integer) session.getAttribute("user");
    %>
    <ul>
        <li><a href="index.jsp">Accueil</a></li>
        <% if (userId != null && Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) { %>
        <li><a href="${pageContext.request.contextPath}/goToAdminPage-servlet">Page admin</a></li>
        <% } %>
        <% if (userId == null) { %>
        <li><a href="${pageContext.request.contextPath}/login">Connexion</a></li>
        <li><a href="${pageContext.request.contextPath}/register">Inscription</a></li>
        <% } else { %>
        <% if (!Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) { %>
        <li><a href="${pageContext.request.contextPath}/userSchedule-servlet">Emploi du temps</a></li>
        <% } %>
        <% if (Role.teacher.equals(UserDAO.getUserById(userId).getUserRole())) { %>
        <li><a href="${pageContext.request.contextPath}/entry-note-servlet?userId=<%= userId %>">Saisie note</a></li>
        <% } %>
        <li><a href="${pageContext.request.contextPath}/personalInformation-servlet">Informations personnelles</a></li>
        <li><a href="${pageContext.request.contextPath}/LogoutServlet">Déconnexion</a></li>
        <% } %>
    </ul>
</div>
</body>
</html>
