<%@ page import="com.example.projetjee.model.dao.RoleDAO" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %><%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 22/11/2024
  Time: 18:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Page admin</title>
</head>
<%
    Integer userId = (Integer) session.getAttribute("user");
    if(userId == null || !"administrator".equals(RoleDAO.getRoleNameById(UserDAO.getUserById(userId).getRoleId()))) {
      response.sendRedirect("index.jsp");
      return;
    }
%>
<body>
<h1>Page admin</h1>
<a href="${pageContext.request.contextPath}/changeRole-servlet">Gestion des rôles</a></br>
<a href="${pageContext.request.contextPath}/lessonManager-servlet">Gérer les séances</a></br>
<a href="${pageContext.request.contextPath}/subjectManager-servlet">Gérer les matières</a></br>
<a href="${pageContext.request.contextPath}/courseManager-servlet">Gérer les cours</a></br>
<a href="${pageContext.request.contextPath}/majorManager-servlet">Gérer les filières</a></br>
<a href="${pageContext.request.contextPath}/classesManager-servlet">Gérer les classes</a></br>
</body>
</html>
