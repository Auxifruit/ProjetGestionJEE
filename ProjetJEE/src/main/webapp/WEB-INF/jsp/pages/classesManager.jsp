<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Classes" %>
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
    <title>Gestion des classes</title>
</head>
<body>
<h1>Liste des classes</h1>
<%
    Integer userId = (Integer) session.getAttribute("user");
    if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
        response.sendRedirect("index.jsp");
        return;
    }

    List<Classes> classList = (List<Classes>) request.getAttribute("classes");

    if (classList == null || classList.isEmpty()) {
%>
<p>Il n'y a pas encore de classe</p>
<%
} else {
%>
<form method="get">
    <table border="1">
        <th>Nom de la classe</th>
        <th>Selection</th>
        <%
            for (Classes classe : classList) {
        %>
        <tr>
            <td><%= classe.getClassName() %></td>
            <td><input type="radio" name="classesId" value="<%= classe.getClassId()%>" required></td>
        </tr>
        <%
            }
        %>
    </table>
    </br>
    <button type="submit" formaction="classesModification-servlet">Modifier</button>
    <button type="submit" formaction="classesDeletion-servlet" onclick="confirmDelete(event)">Supprimer</button>
    <button type="submit" formaction="studentClassesManager-servlet">Assigner un ou plusieurs étudiants à la classe</button>
</form>
<%
    }
%>
<form action="classesCreation-servlet" method="get">
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
        const confirmation = confirm("Êtes-vous sûr de vouloir supprimer la classe ?");

        if (!confirmation) {
            event.preventDefault();
        }
    }
</script>
</html>
