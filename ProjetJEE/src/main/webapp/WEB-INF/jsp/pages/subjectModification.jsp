<%@ page import="com.example.projetjee.model.entities.Subjects" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.dao.RoleDAO" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %><%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 12/11/2024
  Time: 19:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modification de matière</title>
</head>
<body>
<h1>Modification d'une matière</h1>
<%
    Integer userId = (Integer) session.getAttribute("user");
    if(userId == null || !"administrator".equals(RoleDAO.getRoleNameById(UserDAO.getRoleIdByUserID(userId)))) {
        response.sendRedirect(request.getContextPath() + "/returnToIndex-servlet");
        return;
    }

    Subjects subject = (Subjects) request.getAttribute("subject");

    if (subject == null) {
%>
<p>La matière n'existe pas</p>
<%
} else {
%>
<h3>Ancienne information</h3>
<p>Ancien nom de la matière : <%= subject.getSubjectName() %></p>
<form action="subjectModification-servlet" method="post">
    <label>Nouveau nom de la matière : </label>
    <input type="text" name="subjectNewName" required>
    <input name="subjectId" value="<%= subject.getSubjectId() %>" style="visibility: hidden">

    </br></br>
    <button type="submit">Modifier</button>
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
