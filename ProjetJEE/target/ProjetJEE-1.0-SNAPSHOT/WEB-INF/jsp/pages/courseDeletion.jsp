<%@ page import="com.example.projetjee.model.entities.Subjects" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Course" %>
<%@ page import="com.example.projetjee.model.dao.SubjectDAO" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.entities.Role" %><%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 12/11/2024
  Time: 19:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Suppression de cours</title>
</head>
<body>
<h1>Suppression d'un cours</h1>
<%
    Integer userId = (Integer) session.getAttribute("user");
    if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
        response.sendRedirect("index.jsp");
        return;
    }

    List<Course> coursList = (List<Course>) request.getAttribute("courses");

    if (coursList == null || coursList.isEmpty()) {
%>
<p>Il n'y a pas encore de cours</p>
<%
} else {
%>
<h3>Cours existant : </h3>
<form action="courseDeletion-servlet" method="post">
<table border="1">
    <th>Nom du cours</th>
    <th>Nom de la matière</th>
    <th>Selection</th>
    <%
        for (Course course : coursList) {
    %>
    <tr>
        <td><%= course.getCourseName() %></td>
        <td><%= SubjectDAO.getSubjectById(course.getSubjectId()).getSubjectName() %></td>
        <td><input type="radio" name="courseId" value="<%= course.getCourseId() %>" required></td>
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
<% String messageErreur = (String) request.getAttribute("erreur");
    if (messageErreur != null && !messageErreur.isEmpty()) {
%>
<p style='color: red'><%= messageErreur %></p></br>
<%
    }
%>
</body>
</html>
