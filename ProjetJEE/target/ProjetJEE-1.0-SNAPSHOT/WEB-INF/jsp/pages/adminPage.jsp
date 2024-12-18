<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.entities.Role" %><%--
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<%
    Integer userId = (Integer) session.getAttribute("user");
    if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
      response.sendRedirect("index.jsp");
      return;
    }
%>
<body>
<jsp:include page="/elements/sidebar.jsp" />
<div>
<h1>Page admin</h1>
    <div id="OldInfos">
        <ul>
            <li><a href="${pageContext.request.contextPath}/userToValidateManager-servlet">Gérer les inscriptions</a></li>
            <li><a href="${pageContext.request.contextPath}/userManager-servlet?roleFilter=student">Gérer les utilisateurs</a></li>
            <li><a href="${pageContext.request.contextPath}/lessonManager-servlet">Gérer les séances</a></li>
            <li><a href="${pageContext.request.contextPath}/subjectManager-servlet">Gérer les matières</a></li>
            <li><a href="${pageContext.request.contextPath}/courseManager-servlet">Gérer les cours</a></li>
            <li><a href="${pageContext.request.contextPath}/majorManager-servlet">Gérer les filières</a></li>
            <li><a href="${pageContext.request.contextPath}/gradeManager-servlet">Gérer les notes</a></li>
            <li><a href="${pageContext.request.contextPath}/classesManager-servlet">Gérer les classes</a></li>
        </ul>
    </div>
</div>
</body>
</html>
