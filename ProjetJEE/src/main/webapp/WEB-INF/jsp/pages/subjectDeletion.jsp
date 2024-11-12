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
    <title>Suppression de matière</title>
</head>
<body>
<h1>Suppression d'une matière</h1>
<%
    List<Matiere> subjectList = (List<Matiere>) request.getAttribute("subjects");

    if (subjectList == null || subjectList.isEmpty()) {
%>
<p>Il n'y a pas encore de matière</p>
<%
} else {
%>
<h3>Matière existante : </h3>
<form action="subjectDeletion-servlet" method="post">
<table border="1">
    <th>Nom de la matière</th>
    <th>Selection</th>
    <%
        for (Matiere subject : subjectList) {
    %>
    <tr>
        <td><%= subject.getNomMatiere() %></td>
        <td><input type="radio" name="subjectIdToDelete" value="<%= subject.getIdMatiere()%>"></td>
    </tr>
    <%
        }
    %>
</table>
</br>
<button type="submit">Valider</button>
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
