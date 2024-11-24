<%@ page import="com.example.projetjee.model.entities.Subjects" %>
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
    <title>Modification de matière</title>
</head>
<body>
<h1>Modification d'une matière</h1>
<%
    Integer userId = (Integer) session.getAttribute("user");
    if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
        response.sendRedirect("index.jsp");
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
    <button type="submit" onclick="confirmModify(event)">Modifier</button>
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
<script>
    function confirmModify(event) {
        const confirmation = confirm("Êtes-vous sûr de vouloir modifier la matière ?");

        if (!confirmation) {
            event.preventDefault();
        }
    }
</script>
</html>
