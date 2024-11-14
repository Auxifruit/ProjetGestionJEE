<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 12/11/2024
  Time: 20:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestion des séances</title>
</head>
<body>
<h1>Gestion des séances</h1>
<ul>
    <li><a href="${pageContext.request.contextPath}/lessonCreation-servlet">Créer une séance</a></li>
    <li><a href="${pageContext.request.contextPath}/lessonModification-servlet">Modifier une séance</a></li>
    <li><a href="${pageContext.request.contextPath}/lessonDeletion-servlet">Supprimer une séance</a></li>
</ul>
</body>
</html>
