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
<%@ page import="com.example.projetjee.model.entities.Lesson" %>
<%@ page import="com.example.projetjee.model.dao.CourseDAO" %>
<%@ page import="com.example.projetjee.model.entities.Role" %>
<br>
<head>
    <title>Suppression séance</title>
</head>
<body>
<%
  Integer userId = (Integer) session.getAttribute("user");
  if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
    response.sendRedirect("index.jsp");
    return;
  }

  List<Lesson> lessonsList = (List<Lesson>) request.getAttribute("lessons");

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
    for (Lesson lesson : lessonsList) {
  %>
  <tr>
    <td><%= CourseDAO.getCourseName(lesson.getCourseId()) %></td>
    <td><%= UserDAO.getUserById(lesson.getTeacherId()).getUserLastName() + " " + UserDAO.getUserById(lesson.getTeacherId()).getUserName() %></td>
    <td><%= lesson.getLessonStartDate() %></td>
    <td><%= lesson.getLessonEndDate() %></td>
    <td><input type="radio" name="lessonId" value="<%= lesson.getLessonId() %>"></td>
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
