<%@ page import="com.example.projetjee.model.entities.Seance" %>
<%@ page import="com.example.projetjee.model.entities.Classe" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.dao.CourseDAO" %>
<%@ page import="com.example.projetjee.model.dao.LessonClasseDAO" %>
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
<h1>Assignation des séances à des classes</h1>
<%
    Seance lesson = (Seance) request.getAttribute("lesson");

    if (lesson == null) {
%>
<h2>La séance n'existe pas</h2>
<%
} else {
%>
<h3>Informations de la séance :</h3>
<p>Cours :
    <%
        if(lesson.getIdCours() == null || lesson.getIdCours() <= 0) {
    %>
    Il n'y a pas de cours associé à la séance</p>
<%
} else {
%>
    <%= CourseDAO.getCourseName(lesson.getIdCours()) %></p>
<%
    }
%>
<p>Enseignant :
    <%
        if(lesson.getIdEnseignant() == null || lesson.getIdEnseignant() <= 0) {
    %>
    Il n'y a pas d'enseignant associé à la séance</p>
<%
} else {
%>
    <%= " " + UserDAO.getLastNameById(lesson.getIdEnseignant()) + " " + UserDAO.getNameById(lesson.getIdEnseignant()) %></p>
<%
    }
%>
<p>Date de début : <%= lesson.getDateDebutSeance() %></p>
<p>Date de fin : <%= lesson.getDateFinSeance() %></p>
</br>
<%
    List<Classe> availableClasse = (List<Classe>) request.getAttribute("availableClasses");

    if(availableClasse == null || availableClasse.isEmpty()) {
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
        for(Classe classe : availableClasse) {
    %>
    <tr>
        <td><%= classe.getNomClasse() %></td>
        <td><input type="radio" name="classId" value="<%= classe.getIdClasse() %>"></td>
    </tr>
    <%
        }
    %>
</table>
    <input type="text" name="lessonId" value="<%= lesson.getIdSeance() %>" style="visibility: hidden">
    </br>
    <button type="submit">Assigner</button>
</form>
<%
    }

    List<Classe> participatingClass = LessonClasseDAO.getLessonClasses(lesson.getIdSeance());
    if(participatingClass == null || participatingClass.isEmpty()) {
%>
<p>Aucune classe ne participe à la séance</p>
<%
    } else {
%>
<h3>Classe(s) participantes(s) : </h3>
<form action="lessonClassesUnassignation-servlet" method="post">
    <table border="1">
        <tr>
            <th>Nom de la classe</th>
            <th>Selection</th>
        </tr>
        <%
            for(Classe classe : participatingClass) {
        %>
        <tr>
            <td><%= classe.getNomClasse() %></td>
            <td><input type="radio" name="classId" value="<%= classe.getIdClasse() %>"></td>
        </tr>
        <%
            }
        %>
    </table>
    <input type="text" name="lessonId" value="<%= lesson.getIdSeance() %>" style="visibility: hidden">
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
