<%@ page import="com.example.projetjee.model.entities.Matiere" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 12/11/2024
  Time: 19:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modification de matière</title>
</head>
<body>
<h1>Modification d'une matière</h1>
<%
    String subjectName = request.getAttribute("subjectName").toString();
    int subjectId = Integer.parseInt(request.getAttribute("subjectId").toString());

    if (subjectName == null || subjectName.isEmpty()) {
%>
<p>La matière n'a pas de nom/p>
<%
} else {
%>
<h3>Ancienne information</h3>
<p>Ancien nom de la matière : <%= subjectName %></p>
<form action="subjectModification-servlet" method="post">
    <label>Nouveau nom de la matière : </label>
    <input type="text" name="subjectNewName" required>
    <input name="subjectId" value="<%= subjectId %>" style="visibility: hidden">

    </br></br>
    <button type="submit">Modifier</button>
</form>
<%
    }
%>
<% String messageErreur = (String) request.getAttribute("erreur");
    if (messageErreur != null && !messageErreur.isEmpty()) {
%>
<p style='color: red'><%= messageErreur %></p></br>
<%
    }
%>
</body>
</html>
