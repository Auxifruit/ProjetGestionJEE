<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 07/11/2024
  Time: 15:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Utilisateur" %>
<html>
<head>
    <title>Liste utilisateurs</title>
</head>
<body>
<h1>List des utilisateurs</h1>

<%
    List<Utilisateur> userList = (List<Utilisateur>) request.getAttribute("users");
%>

<%
    if (userList == null || userList.isEmpty()) {
%>
<h2>Pas d'utilisateurs</h2>
<%
} else {
%>
<span><b>Filtrer par : </b></span>
<form method="GET" action="user-servlet">
    <select name="roleFilter">
        <option value="">Tous</option>
        <option value="ETUDIANT">Étudiants</option>
        <option value="ENSEIGNANT">Enseignants</option>
        <option value="ADMINISTRATEUR">Administrateurs</option>
    </select>
    <button type="submit">Valider</button>
</form></br></br>
<table border="1px">
    <tr>
        <th>Id utilisateur</th>
        <th>Email utilisateur</th>
        <th>Rôle utilisateur</th>
        <th>Selection</th>
    </tr>
    <%
        for (Utilisateur user : userList) {
    %>
    <tr>
        <td><%= user.getIdUtilisateur() %></td>
        <td><%= user.getIdentifiantUtilisateur() %></td>
        <td><%= user.getRoleUtilisateur() %></td>
        <td><input type="radio" name="user" value=user.getIdUtilisateur()></td>
    </tr>
    <%
        }
    %>
</table></br>
<span><b>Nouveau rôle : </b></span>
<select id="new_role">
    <option value="student">Étudiant</option>
    <option value="teacher">Enseignant</option>
    <option value="admin">Administrateur</option>
</select>
<button>Valider</button></br></br>
<%
    }
%>
</body>
</html>
