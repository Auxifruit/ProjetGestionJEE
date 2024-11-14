<%@ page import="com.example.projetjee.model.entities.Matiere" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Cours" %>
<%@ page import="com.example.projetjee.model.dao.SubjectDAO" %><%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 12/11/2024
  Time: 19:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Création de cours</title>
</head>
<body>
<h1>Création d'un nouveau cours</h1>
<%
    List<Cours> coursList = (List<Cours>) request.getAttribute("courses");
    List<Matiere> subjectList = (List<Matiere>) request.getAttribute("subjects");

    if (coursList == null || coursList.isEmpty()) {
%>
<p>Il n'y a pas encore de cours</p>
<%
} else {
%>
<h3>Cours existant : </h3>
<table border="1">
    <th>Nom du cours</th>
    <th>Nom de la matière</th>
    <%
        for (Cours course : coursList) {
    %>
    <tr>
        <td><%= course.getNomCours() %></td>
        <td><%= SubjectDAO.getSubjectNameById(course.getIdMatiere()) %></td>
    </tr>
    <%
        }
    %>
</table>
<%
    }
%>
<h3>Nouveau cours :</h3>
<%
    if(subjectList == null || subjectList.isEmpty()) {
%>
<p>Il n'y a de matière disponible pour créer un cours</p>
<%
    }
    else {
%>
<form action="courseCreation-servlet" method="post">
    <label>Choix de la matière : </label>
    <select name="courseSubjectId" required>
        <%
            for (Matiere subject : subjectList) {
        %>
        <option value="<%= subject.getIdMatiere() %>"><%= subject.getNomMatiere() %></option>
        <%
            }
        %>
    </select>
    </br></br>
    <label>Choix du nom du cours : </label>
    <input type="text" name="courseName" required/>
    </br></br>
    <button type="submit">Créer</button>
</form>
<%
    }
%>
<% String messageErreur = (String) request.getAttribute("erreur");
    if (messageErreur != null && !messageErreur.isEmpty()) {
%>
<p style='color: red'><%= messageErreur %>
</p></br>
<%
    }
%>
</body>
</html>
