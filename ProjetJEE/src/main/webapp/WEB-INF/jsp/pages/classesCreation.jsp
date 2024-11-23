<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Classes" %>
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
    <title>Création de filière</title>
</head>
<body>
<h1>Création d'une nouvelle filière</h1>
<%
    Integer userId = (Integer) session.getAttribute("user");
    if(userId == null || !"administrator".equals(RoleDAO.getRoleNameById(UserDAO.getUserById(userId).getRoleId()))) {
        response.sendRedirect("index.jsp");
        return;
    }

    List<Classes> classesList = (List<Classes>) request.getAttribute("classes");

    if (classesList == null || classesList.isEmpty()) {
%>
<p>Il n'y a pas encore de classe</p>
<%
} else {
%>
<h3>Classe existante : </h3>
<table border="1">
    <th>Nom de la classe</th>
    <%
        for (Classes classe : classesList) {
    %>
    <tr>
        <td><%= classe.getClassName() %></td>
    </tr>
    <%
        }
    %>
</table>
<%
    }
%>
<h3>Nouvelle classe :</h3>
<form action="classesCreation-servlet" method="post">
    <label>Choix du nom de la classe : </label>
    <input type="text" name="newClasses" required/>
    </br></br>
    <button type="submit">Créer</button>
</form>
<% String messageErreur = (String) request.getAttribute("erreur");
    if (messageErreur != null && !messageErreur.isEmpty()) {
%>
<p style='color: red'><%= messageErreur %>
</p></br>
<%
    }
%>
</body>
</html>
