<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 07/11/2024
  Time: 15:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Users" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.entities.Role" %>
<html>
<head>
    <title>Liste Userss</title>
</head>
<body>
<h1>List des Userss</h1>

<%
    Integer userId = (Integer) session.getAttribute("user");
    if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
        response.sendRedirect("index.jsp");
        return;
    }

    List<Users> userList = (List<Users>) request.getAttribute("users");

    if(userList == null || userList.isEmpty()) {
%>
<h2>Pas d'Userss</h2>
<%
} else {
%>
<span><b>Filtrer par : </b></span>
<form action="changeRole-servlet" method="get" >
    <select name="roleFilter">
        <option value="">Tous</option>
        <option value=<%= Role.student %>>Étudiants</option>
        <option value="<%= Role.teacher %>">Enseignants</option>
        <option value="<%= Role.administrator %>">Administrateurs</option>
    </select>
    <button type="submit">Valider</button>
</form>
</br></br>
<form action="changeRole-servlet" method="post">
<table border="1px">
    <tr>
        <th>Id Users</th>
        <th>Nom Users</th>
        <th>Prenom Users</th>
        <th>Email Users</th>
        <th>Rôle Users</th>
        <th>Selection</th>
    </tr>
    <%
        for (Users user : userList) {
    %>
    <tr>
        <td><%= user.getUserId() %>
        </td>
        <td><%= user.getUserLastName() %>
        </td>
        <td><%= user.getUserName() %>
        </td>
        <td><%= user.getUserEmail() %>
        </td>
        <td><%= user.getUserRole().toString() %>
        </td>
        <td><input type="radio" name="user" value="<%= user.getUserId() %>" required></td>
    </tr>
    <%
        }
    %>
</table>
</br>
<span><b>Nouveau rôle : </b></span>
    <select name="newRoleID" required>
        <option value=<%= Role.student %>>Étudiants</option>
        <option value="<%= Role.teacher %>">Enseignants</option>
        <option value="<%= Role.administrator %>">Administrateurs</option>
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
