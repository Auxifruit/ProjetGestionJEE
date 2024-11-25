<%@ page import="com.example.projetjee.model.entities.Subjects" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.entities.Role" %>
<%@ page import="com.example.projetjee.model.entities.Grade" %>
<%@ page import="com.example.projetjee.model.dao.CourseDAO" %>
<%@ page import="com.example.projetjee.model.dao.StudentDAO" %>
<%@ page import="com.example.projetjee.model.dao.ClasseDAO" %><%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 15/11/2024
  Time: 19:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestion des matières</title>
</head>
<script src="${pageContext.request.contextPath}/js/filterTable.js"></script>
<body>
<h1>Liste des notes</h1>
<label for="searchInput">Rechercher :</label>
<input type="text" id="searchInput" onkeyup="filterTable()" placeholder="Recherche">
</br></br>
<%
    Integer userId = (Integer) session.getAttribute("user");
    if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
        response.sendRedirect("index.jsp");
        return;
    }

    List<Grade> gradeList = (List<Grade>) request.getAttribute("grades");

    if (gradeList == null || gradeList.isEmpty()) {
%>
<p>Il n'y a pas encore de notes</p>
<%
} else {
%>
<form method="get">
    <table border="1">
        <th>Nom de la note</th>
        <th>Nom du cours</th>
        <th>Valeur de la note</th>
        <th>Coefficient de la note</th>
        <th>Nom et prénom de l'étudiant</th>
        <th>Classe de l'étudiant</th>
        <th>Nom et prénom de l'enseignant</th>
        <th>Selection</th>
        <%
            for (Grade grade : gradeList) {
                Integer courseId = grade.getCourseId();
                Integer teacherId = grade.getTeacherId();
        %>
        <tr>
            <td><%= grade.getGradeName() %></td>
            <td>
                <% if(courseId == null) {
                %>
                Pas de cours associé
                <%
                } else {
                %>
                <%= CourseDAO.getCourseById(courseId).getCourseName() %>
                <%
                    }
                %>
            </td>
            <td><%= grade.getGradeValue() %></td>
            <td><%= grade.getGradeCoefficient() %></td>
            <td><%= UserDAO.getUserById(grade.getStudentId()).getUserLastName() + " " + UserDAO.getUserById(grade.getStudentId()).getUserName() %></td>
            <td><%= ClasseDAO.getClasseById(StudentDAO.getStudentById(grade.getStudentId()).getClassId()).getClassName() %></td>
            <td>
                <% if(teacherId == null) {
                %>
                Pas d'enseignant associé
                <%
                } else {
                %>
                <%= UserDAO.getUserById(teacherId).getUserLastName() + " " + UserDAO.getUserById(teacherId).getUserName() %>
                <%
                    }
                %>
            </td>
            <td><input type="radio" name="gradeId" value="<%= grade.getGradeId()%>" required></td>
        </tr>
        <%
            }
        %>
    </table>
    </br>
    <button type="submit" formaction="gradeModification-servlet">Modifier</button>
    <button type="submit" formaction="gradeDeletion-servlet" onclick="confirmDelete(event)">Supprimer</button>
</form>
<form action="gradeCreation-servlet" method="get">
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
<script>
    function confirmDelete(event) {
        const confirmation = confirm("Êtes-vous sûr de vouloir supprimer la note ?");

        if (!confirmation) {
            event.preventDefault();
        }
    }
</script>
</html>
