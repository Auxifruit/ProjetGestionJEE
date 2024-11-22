<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.dao.RoleDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<%
  Integer userId = (Integer) session.getAttribute("user");
  String role = null;
  if(userId != null) {
    role = RoleDAO.getRoleNameById(UserDAO.getRoleIdByUserID(userId));
  }
%>
<h1><%= "Hello World!" %></h1>
<br/>
<a href="${pageContext.request.contextPath}/hello-servlet">Hello Servlet</a></br>
<%
  if(userId != null && "administrator".equals(RoleDAO.getRoleNameById(UserDAO.getRoleIdByUserID(userId)))) {
%>
  <a href="${pageContext.request.contextPath}/goToAdminPage-servlet">Page admin</a></br>
<%
  }
%>
<a href="${pageContext.request.contextPath}/studentSchedule-servlet?studentId=2">Emplois du temps de l'étudiant n°2</a></br>
<%
  if(userId == null) {
%>
  <a href="${pageContext.request.contextPath}/login">Connexion</a></br>
<%
  } else {
%>
  <a href="${pageContext.request.contextPath}/LogoutServlet">Deconexion</a></br>
<%
  }
%>
<a href="${pageContext.request.contextPath}/register">Inscription</a></br>
</body>
</html>