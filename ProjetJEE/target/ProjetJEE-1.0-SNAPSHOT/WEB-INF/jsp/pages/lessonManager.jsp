<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Lesson" %>
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
    <title>Gestion des séance</title>
</head>
<body>
<h1>Liste des séance</h1>
<%
    List<Lesson> lessonList = (List<Lesson>) request.getAttribute("lessons");

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
            for (Lesson lesson : lessonList) {
        %>
        <tr>
            <td><%= CourseDAO.getCourseName(lesson.getCourseId()) %></td>
            <td><%= UserDAO.getLastNameById(lesson.getTeacherId()) + " " + UserDAO.getNameById(lesson.getTeacherId()) %></td>
            <td><%= lesson.getLessonStartDate() %></td>
            <td><%= lesson.getLessonEndDate() %></td>
            <td><input type="radio" name="lessonId" value="<%= lesson.getLessonId()%>" required></td>
        </tr>
        <%
            }
        %>
    </table>
    </br>
    <button type="submit" formaction="lessonModification-servlet">Modifier</button>
    <button type="submit" formaction="lessonDeletion-servlet">Supprimer</button>
    <button type="submit" formaction="lessonClassesManager-servlet">Assigner une ou plusieurs classes à la séance</button>
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