<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Cours" %>
<%@ page import="com.example.projetjee.model.dao.SubjectDAO" %>
<%@ page import="com.example.projetjee.model.entities.Seance" %>
<%@ page import="com.example.projetjee.model.dao.CourseDAO" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %><%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 15/11/2024
  Time: 19:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestion des cours</title>
</head>
<body>
<h1>Liste des cours</h1>
<%
    List<Seance> lessonList = (List<Seance>) request.getAttribute("lessons");

    if (lessonList == null || lessonList.isEmpty()) {
%>
<p>Il n'y a pas encore de cours</p>
<%
} else {
%>
<form method="get">
    <table border="1">
        <tr>
            <th>Nom du cours</th>
            <th>Nom et prénom du professeur</th>
            <th>Date de début</th>
            <th>Date de fin</th>
            <th>Selection</th>
        </tr>
        <%
            for (Seance lesson : lessonList) {
        %>
        <tr>
            <td><%= CourseDAO.getCourseName(lesson.getIdCours()) %></td>
            <td><%= UserDAO.getLastNameById(lesson.getIdEnseignant()) + " " + UserDAO.getNameById(lesson.getIdEnseignant()) %></td>
            <td><%= lesson.getDateDebutSeance() %></td>
            <td><%= lesson.getDateFinSeance() %></td>
            <td><input type="radio" name="lessonId" value="<%= lesson.getIdSeance()%>" required></td>
        </tr>
        <%
            }
        %>
    </table>
    </br>
    <button type="submit" formaction="lessonModification-servlet">Modifier</button>
    <button type="submit" formaction="lessonDeletion-servlet">Supprimer</button>
</form>
<form action="lessonCreation-servlet" method="get">
    <button type="submit">Créer</button>
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
