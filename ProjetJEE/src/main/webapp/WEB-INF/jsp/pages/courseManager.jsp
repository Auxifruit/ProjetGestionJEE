<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Course" %>
<%@ page import="com.example.projetjee.model.dao.SubjectDAO" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.entities.Role" %><%--
  Created by IntelliJ IDEA.-
  User: CYTech Student
  Date: 15/11/2024
  Time: 19:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestion des cours</title>
</head>
<script src="${pageContext.request.contextPath}/js/filterTable.js"></script>
<body>
<h1>Liste des cours</h1>
<label for="searchInput">Rechercher :</label>
<input type="text" id="searchInput" onkeyup="filterTable()" placeholder="Recherche">
</br></br>
<%
    Integer userId = (Integer) session.getAttribute("user");
    if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
        response.sendRedirect("index.jsp");
        return;
    }

    List<Course> courseList = (List<Course>) request.getAttribute("courses");

    if (courseList == null || courseList.isEmpty()) {
%>
<p>Il n'y a pas encore de cours</p>
<%
} else {
%>
<form method="get">
    <table border="1">
        <th>Nom du cours</th>
        <th>Nom de la matière du cours</th>
        <th>Selection</th>
        <%
            for (Course course : courseList) {
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
            <td><input type="radio" name="courseId" value="<%= course.getCourseId()%>" required></td>
        </tr>
        <%
            }
        %>
    </table>
    </br>
    <button type="submit" formaction="courseModification-servlet">Modifier</button>
    <button type="submit" formaction="courseDeletion-servlet" onclick="confirmDelete(event)">Supprimer</button>
</form>
<form action="courseCreation-servlet" method="get">
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
        const confirmation = confirm("Êtes-vous sûr de vouloir supprimer le cours ?");

        if (!confirmation) {
            event.preventDefault();
        }
    }
</script>
</html>
