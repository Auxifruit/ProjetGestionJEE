<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.entities.Role" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Accueil</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div>
<%
  Integer userId = (Integer) session.getAttribute("user");
%>
  <h1><%= "Hello World!" %></h1>
  <br/>
  <%
    if(userId != null && Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
  %>
    <a href="${pageContext.request.contextPath}/goToAdminPage-servlet">Page admin</a></br>
  <%
    }
  %>
  <%
    if(userId == null) {
  %>
    <a href="${pageContext.request.contextPath}/login">Connexion</a></br>
    <a href="${pageContext.request.contextPath}/register">Inscription</a></br>
  <%
    } else {
      if(!Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
  %>
    <a href="${pageContext.request.contextPath}/userSchedule-servlet">Emploi du temps</a></br>
    <%
      }
      if(Role.teacher.equals(UserDAO.getUserById(userId).getUserRole())) {
    %>
    <a href="${pageContext.request.contextPath}/entry-note-servlet?userId=<%= userId.intValue() %>">Saisie note</a></br>
    <%
      }
    %>
    <a href="${pageContext.request.contextPath}/personalInformation-servlet">Informations personnelles</a></br>
    <a href="${pageContext.request.contextPath}/LogoutServlet">Deconexion</a></br>
  <%
    }
  %>
</div>
</body>
</html>
