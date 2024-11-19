<%@ page import="com.example.projetjee.model.entities.Lesson" %>
<%@ page import="com.example.projetjee.model.entities.Classes" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.dao.CourseDAO" %>
<%@ page import="com.example.projetjee.model.dao.LessonClassesDAO" %>
<%--
Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 15/11/2024
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Assigner des cours</title>
</head>
<body>
<h1>Assignation des séances à des Classs</h1>
<%
    Lesson lesson = (Lesson) request.getAttribute("lesson");

    if (lesson == null) {
%>
<h2>La séance n'existe pas</h2>
<%
} else {
%>
<h3>Informations de la séance :</h3>
<p>Cours :
    <%
        if(lesson.getCourseId() == null || lesson.getCourseId() <= 0) {
    %>
    Il n'y a pas de cours associé à la séance</p>
<%
} else {
%>
    <%= CourseDAO.getCourseName(lesson.getCourseId()) %></p>
<%
    }
%>
<p>Enseignant :
    <%
        if(lesson.getTeacherId() == null || lesson.getTeacherId() <= 0) {
    %>
    Il n'y a pas d'enseignant associé à la séance</p>
<%
} else {
%>
    <%= " " + UserDAO.getLastNameById(lesson.getTeacherId()) + " " + UserDAO.getNameById(lesson.getTeacherId()) %></p>
<%
    }
%>
<p>Date de début : <%= lesson.getLessonStartDate() %></p>
<p>Date de fin : <%= lesson.getLessonEndDate() %></p>
</br>
<%
    List<Classes> availableClass = (List<Classes>) request.getAttribute("availableClasses");

    if(availableClass == null || availableClass.isEmpty()) {
%>
<p>Aucune classe n'est disponible pour la séance</p>
<%
    } else {
%>
<h3>Classe(s) disponible(s) : </h3>
<form action="lessonClassesAssignation-servlet" method="post">
<table border="1">
    <tr>
        <th>Nom de la classe</th>
        <th>Selection</th>
    </tr>
    <%
        for(Classes classes : availableClass) {
    %>
    <tr>
        <td><%= classes.getClassName() %></td>
        <td><input type="radio" name="classId" value="<%= classes.getClassId() %>"></td>
    </tr>
    <%
        }
    %>
</table>
    <input type="text" name="lessonId" value="<%= lesson.getLessonId() %>" style="visibility: hidden">
    </br>
    <button type="submit">Assigner</button>
</form>
<%
    }

    List<Classes> participatingClass = LessonClassesDAO.getLessonClasses(lesson.getLessonId());
    if(participatingClass == null || participatingClass.isEmpty()) {
%>
<p>Aucune Class ne participe à la séance</p>
<%
    } else {
%>
<h3>Class(s) participantes(s) : </h3>
<form action="lessonClassesUnassignation-servlet" method="post">
    <table border="1">
        <tr>
            <th>Nom de la classe</th>
            <th>Selection</th>
        </tr>
        <%
            for(Classes classes : participatingClass) {
        %>
        <tr>
            <td><%= classes.getClassName() %></td>
            <td><input type="radio" name="classId" value="<%= classes.getClassId() %>"></td>
        </tr>
        <%
            }
        %>
    </table>
    <input type="text" name="lessonId" value="<%= lesson.getLessonId() %>" style="visibility: hidden">
    </br>
    <button type="submit">Désassigner</button>
</form>
<%
    }
%>
<% String messageErreur = (String) request.getAttribute("erreur");
    if(messageErreur != null && !messageErreur.isEmpty()) {
%>
<p style='color: red'><%= messageErreur %></p></br>
<%
    }
}
%>
</body>
</html>
