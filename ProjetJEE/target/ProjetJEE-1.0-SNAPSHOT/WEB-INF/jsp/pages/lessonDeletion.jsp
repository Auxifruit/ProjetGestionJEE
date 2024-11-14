<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 10/11/2024
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.entities.Seance" %>
<%@ page import="com.example.projetjee.model.dao.CourseDAO" %>
<br>
<head>
    <title>Suppression séance</title>
</head>
<body>
<%
  List<Seance> lessonsList = (List<Seance>) request.getAttribute("lessons");

  if (lessonsList == null || lessonsList.isEmpty()) {
%>
<h2>Pas de séance à supprimer</h2>
<%
} else {
%>
<form action="lessonDeletion-servlet" method="post">
<h1>Suppression d'une séance</h1>
<label>Choix de la séance : </label>
<table border="1">
  <tr>
    <th>Nom du cours</th>
    <th>Nom et prénom du professeur</th>
    <th>Date de début</th>
    <th>Date de fin</th>
    <th>Selection</th>
  </tr>
  <%
    for (Seance lesson : lessonsList) {
  %>
  <tr>
    <td><%= CourseDAO.getCourseName(lesson.getIdCours()) %></td>
    <td><%= UserDAO.getLastNameById(lesson.getIdEnseignant()) + " " + UserDAO.getNameById(lesson.getIdEnseignant()) %></td>
    <td><%= lesson.getDateDebutSeance() %></td>
    <td><%= lesson.getDateFinSeance() %></td>
    <td><input type="radio" name="lessonId" value="<%= lesson.getIdSeance() %>"></td>
  </tr>
  <%
    }
  %>
</table>
</br></br>
  <button type="submit">Supprimer</button>
</form>
</br></br>
<%
  }
%>
</body>
</html>
