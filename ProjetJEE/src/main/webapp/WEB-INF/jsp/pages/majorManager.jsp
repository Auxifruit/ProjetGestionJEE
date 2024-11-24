<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Major" %>
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
    <title>Gestion des filières</title>
</head>
<body>
<h1>Liste des filières</h1>
<%
    Integer userId = (Integer) session.getAttribute("user");
    if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
        response.sendRedirect("index.jsp");
        return;
    }

    List<Major> majorList = (List<Major>) request.getAttribute("majors");

    if (majorList == null || majorList.isEmpty()) {
%>
<p>Il n'y a pas encore de filière</p>
<%
} else {
%>
<form method="get">
    <table border="1">
        <th>Nom de la filière</th>
        <th>Selection</th>
        <%
            for (Major major : majorList) {
        %>
        <tr>
            <td><%= major.getMajorName() %></td>
            <td><input type="radio" name="majorId" value="<%= major.getMajorId()%>" required></td>
        </tr>
        <%
            }
        %>
    </table>
    </br>
    <button type="submit" formaction="majorModification-servlet">Modifier</button>
    <button type="submit" formaction="majorDeletion-servlet" onclick="confirmDelete(event)">Supprimer</button>
</form>
<%
    }
%>
<form action="majorCreation-servlet" method="get">
    <button type="submit">Créer</button>
</form>
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
        const confirmation = confirm("Êtes-vous sûr de vouloir supprimer la filière ?");

        if (!confirmation) {
            event.preventDefault();
        }
    }
</script>
</html>
