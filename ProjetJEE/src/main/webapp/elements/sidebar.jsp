<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.entities.Role" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>sidebar</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/styles.css">
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
        color: #283348;
        text-decoration: none;
        font-size: 16px;
        padding: 15px;
        display: block;
        border-radius: 20px;
        background-color: #C4DFE9;
        text-align: center;
        transition: background-color 0.3s ease, border-radius 0.3s ease;
    }
    .sidebar ul li a:hover {
        background-color: #A6BBD6;
        border-radius: 25px;
    }

    .sidebar img {
        display: block;
        margin: 0 auto;
    }

    #return_button {
        position: fixed;
        top: 10px;
        left: 290px;
        background: none;
        border: none;
        cursor: pointer;
        z-index: 1000;
    }

    .button_icon {
        width: 30px;
        height: 30px;
        transition: transform 0.3s ease;
    }

    #return_button:hover .button_icon {
        transform: scale(1.2);
    }
</style>
<body>
<div class="sidebar">
    <%
        Integer userId = (Integer) session.getAttribute("user");
    %>
    <a href="index.jsp"><img src="${pageContext.request.contextPath}/css/logo.png" alt="logo" style="width:120px;height:120px;"></a>
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
        <li><a href="${pageContext.request.contextPath}/teacherGrade-servlet">Voir les notes saisies</a></li>
        <% } %>
        <% if (Role.student.equals(UserDAO.getUserById(userId).getUserRole())) { %>
        <li><a href="${pageContext.request.contextPath}/student-grade-report-servlet?userId=<%= userId %>">Ses notes</a></li>
        <% } %>
        <li><a href="${pageContext.request.contextPath}/personalInformation-servlet">Informations personnelles</a></li>
        <li><a href="${pageContext.request.contextPath}/LogoutServlet">Déconnexion</a></li>
        <% } %>
    </ul>
</div>
<button id="return_button" onclick="history.back()">
    <img src="${pageContext.request.contextPath}/css/return_button.png" alt="Retour" class="button_icon">
</button>

</body>
</html>
