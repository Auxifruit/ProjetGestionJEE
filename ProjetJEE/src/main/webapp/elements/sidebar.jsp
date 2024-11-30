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
        background-color: #283348; /* Fond de la sidebar */
        color: white;
        padding: 20px;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
        top: 0;
        left: 0;
        z-index: 1000;
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
        color: #283348; /* Couleur du texte du lien */
        text-decoration: none;
        font-size: 16px;
        padding: 15px; /* Augmenté pour plus d'espace autour du texte */
        display: block;
        border-radius: 20px; /* Coins très arrondis */
        background-color: #C4DFE9; /* Arrière-plan des liens */
        text-align: center; /* Centrer le texte horizontalement */
        transition: background-color 0.3s ease, border-radius 0.3s ease; /* Transition pour le survol */
    }

    .sidebar ul li a:hover {
        background-color: #A6BBD6; /* Fond bleu clair au survol */
        border-radius: 25px; /* Augmenter encore l'arrondi au survol */
    }

    /* Centrer l'image dans la sidebar */
    .sidebar img {
        display: block;
        margin: 0 auto;
    }
</style>
<body>
<div class="sidebar">
    <%
        Integer userId = (Integer) session.getAttribute("user");
    %>
    <center><a href="index.jsp"><img src="${pageContext.request.contextPath}/css/logo.png" alt="logo" style="width:120px;height:120px;"></a></center>
    </br></br>
    <div style="background-color: #333333; height: 2px;" ></div>
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
        <% if (Role.student.equals(UserDAO.getUserById(userId).getUserRole())) { %>
        <li><a href="${pageContext.request.contextPath}/student-grade-report-servlet?userId=<%= userId %>">Ses notes</a></li>
        <% } %>
        <li><a href="${pageContext.request.contextPath}/personalInformation-servlet">Informations personnelles</a></li>
        <li><a href="${pageContext.request.contextPath}/LogoutServlet">Déconnexion</a></li>
        <% } %>
    </ul>
</div>
</body>
</html>
