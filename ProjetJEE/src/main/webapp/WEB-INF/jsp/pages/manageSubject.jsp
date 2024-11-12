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
    <title>Gestion des matières</title>
</head>
<body>
<h1>Gestion des matières</h1>
<ul>
    <li><a href="${pageContext.request.contextPath}/subjectCreation-servlet">Créer une matière</a></li>
    <li><a href="${pageContext.request.contextPath}/subjectModification-servlet">Modifier une matière</a></li>
    <li><a href="${pageContext.request.contextPath}/subjectDeletion-servlet">Supprimer une matière</a></li>
</ul>
</body>
</html>
