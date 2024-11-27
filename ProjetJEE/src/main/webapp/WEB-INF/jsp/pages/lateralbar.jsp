<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.entities.Role" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <title>Barre Latérale</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
</head>
<body>
<div class="sidebar">
  <h2>Menu</h2>
  <%
    Integer userId = (Integer) session.getAttribute("user");
  %>
  <ul>
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
