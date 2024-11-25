<%@ page import="com.example.projetjee.model.entities.Subjects" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.entities.Role" %><%--
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
<h1>Liste des matières</h1>
<label for="searchInput">Rechercher :</label>
<input type="text" id="searchInput" onkeyup="filterTable()" placeholder="Recherche">
</br></br>
<%
    Integer userId = (Integer) session.getAttribute("user");
    if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
        response.sendRedirect("index.jsp");
        return;
    }

    List<Subjects> subjectList = (List<Subjects>) request.getAttribute("subjects");

    if (subjectList == null || subjectList.isEmpty()) {
%>
<p>Il n'y a pas encore de matière</p>
<%
} else {
%>
<form method="get">
    <table border="1">
        <th>Nom de la matière</th>
        <th>Selection</th>
        <%
            for (Subjects subject : subjectList) {
        %>
        <tr>
            <td><%= subject.getSubjectName() %></td>
            <td><input type="radio" name="subjectId" value="<%= subject.getSubjectId()%>" required></td>
        </tr>
        <%
            }
        %>
    </table>
    </br>
    <button type="submit" formaction="subjectModification-servlet">Modifier</button>
    <button type="submit" formaction="subjectDeletion-servlet" onclick="confirmDelete(event)">Supprimer</button>
</form>
<form action="subjectCreation-servlet" method="get">
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
        const confirmation = confirm("Êtes-vous sûr de vouloir supprimer la matière ?");

        if (!confirmation) {
            event.preventDefault();
        }
    }
</script>
</html>
