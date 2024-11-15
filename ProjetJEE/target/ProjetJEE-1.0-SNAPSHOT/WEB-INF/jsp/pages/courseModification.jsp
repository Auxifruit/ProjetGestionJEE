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
    <title>Modification de cours</title>
</head>
<body>
<h1>Modification d'un cours</h1>
<%
    Cours course = (Cours) request.getAttribute("course");
    List<Matiere> subjectList = (List<Matiere>) request.getAttribute("subjects");

    if (course == null) {
%>
<p>Le cours n'existe pas</p>
<%
} else {
%>
<h3>Anciennes informations</h3>
<p>Ancien nom : <%= course.getNomCours() %></p>
<p>Ancienne matière : <%= SubjectDAO.getSubjectNameById(course.getIdMatiere()) %></p>

<form action="courseModification-servlet" method="post">

<%
    if(subjectList == null || subjectList.isEmpty()) {
%>
<p>Il n'y a de matière disponible pour modifier le cours</p>
<%
    }
    else {
%>
    <label>Choix de la matière : </label>
    <select name="newCourseSubjectId">
        <option value="">Ne pas modifier la matière</option>
        <%
            for (Matiere subject : subjectList) {
        %>
        <option value="<%= subject.getIdMatiere() %>"><%= subject.getNomMatiere() %></option>
        <%
            }
        %>
    </select>
    </br></br>
    <label>Choix du nouveau nom du cours : </label>
    <input type="text" name="newCourseName"/>
    <input name="courseId" value="<%= course.getIdCours() %>" style="visibility: hidden">
    </br></br>
    <button type="submit">Modifier</button>
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
    }
%>
</body>
</html>
