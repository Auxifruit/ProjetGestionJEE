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
    <title>Création de cours</title>
</head>
<body>
<h1>Création d'un nouveau cours</h1>
<%
    Integer userId = (Integer) session.getAttribute("user");
    if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
        response.sendRedirect("index.jsp");
        return;
    }

    List<Course> coursList = (List<Course>) request.getAttribute("courses");
    List<Subjects> subjectList = (List<Subjects>) request.getAttribute("subjects");

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
        for (Course course : coursList) {
            Integer subjectId = course.getSubjectId();
    %>
    <tr>
        <td><%= course.getCourseName() %></td>
        <td>
            <% if(subjectId == null) {
            %>
            Pas de matière associé
            <%
            } else {
            %>
            <%= SubjectDAO.getSubjectById(subjectId).getSubjectName() %>
            <%
                }
            %>
        </td>
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
            for (Subjects subject : subjectList) {
        %>
        <option value="<%= subject.getSubjectId() %>"><%= subject.getSubjectName() %></option>
        <%
            }
        %>
    </select>
    </br></br>
    <label>Choix du nom du cours : </label>
    <input type="text" name="courseName" required/>
    </br></br>
    <button type="submit" onclick="confirmCreate(event)">Créer</button>
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
<script>
    function confirmCreate(event) {
        const confirmation = confirm("Êtes-vous sûr de vouloir créer le cours ?");

        if (!confirmation) {
            event.preventDefault();
        }
    }
</script>
</html>
