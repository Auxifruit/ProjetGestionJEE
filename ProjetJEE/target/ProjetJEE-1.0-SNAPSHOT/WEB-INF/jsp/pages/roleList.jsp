<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 07/11/2024
  Time: 15:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Utilisateur" %>
<%@ page import="com.example.projetjee.model.dao.RoleDAO" %>
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
<form action="user-servlet" method="post" >
    <select name="roleFilter">
        <option value="">Tous</option>
        <option value="1">Étudiants</option>
        <option value="2">Enseignants</option>
        <option value="3">Administrateurs</option>
    </select>
    <button type="submit">Valider</button>
</form>
</br></br>
<form action="changeRole-servlet" method="post">
<table border="1px">
    <tr>
        <th>Id utilisateur</th>
        <th>Nom utilisateur</th>
        <th>Prenom utilisateur</th>
        <th>Email utilisateur</th>
        <th>Rôle utilisateur</th>
        <th>Selection</th>
    </tr>
    <%
        for (Utilisateur user : userList) {
    %>
    <tr>
        <td><%= user.getIdUtilisateur() %>
        </td>
        <td><%= user.getNomUtilisateur() %>
        </td>
        <td><%= user.getPrénomUtilisateur() %>
        </td>
        <td><%= user.getEmailUtilisateur() %>
        </td>
        <td><%= RoleDAO.getRoleNameById(user.getIdRole()) %>
        </td>
        <td><input type="radio" name="user" value="<%= user.getIdUtilisateur() %>"></td>
    </tr>
    <%
        }
    %>
</table>
</br>
<span><b>Nouveau rôle : </b></span>
    <select name="newRoleID">
        <option value="1">Étudiant</option>
        <option value="2">Enseignant</option>
        <option value="3">Administrateur</option>
    </select>
    <button type="submit">Valider</button>
    </br></br>
</form>
<% String messageErreur = (String) request.getAttribute("erreur");
    if(messageErreur != null && !messageErreur.isEmpty()) {
%>
<p style='color: red'><%= messageErreur %></p></br>
<%
    }
    }
%>
</body>
</html>
