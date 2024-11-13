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
    <title>Gestion des cours</title>
</head>
<body>
<h1>Gestion des cours</h1>
<ul>
    <li><a href="${pageContext.request.contextPath}/courseCreation-servlet">Créer un cours</a></li>
    <li><a href="${pageContext.request.contextPath}/courseModification-servlet">Modifier un cours</a></li>
    <li><a href="${pageContext.request.contextPath}/courseDeletion-servlet">Supprimer un cours</a></li>
</ul>
</body>
</html>
