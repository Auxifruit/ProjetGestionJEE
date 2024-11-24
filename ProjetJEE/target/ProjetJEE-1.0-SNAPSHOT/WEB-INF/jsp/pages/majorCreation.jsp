<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Major" %>
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

    List<Major> majorList = (List<Major>) request.getAttribute("majors");

    if (majorList == null || majorList.isEmpty()) {
%>
<p>Il n'y a pas encore de filière</p>
<%
} else {
%>
<h3>Filière existante : </h3>
<table border="1">
    <th>Nom de la filière</th>
    <%
        for (Major major : majorList) {
    %>
    <tr>
        <td><%= major.getMajorName() %></td>
    </tr>
    <%
        }
    %>
</table>
<%
    }
%>
<h3>Nouvelle filière :</h3>
<form action="majorCreation-servlet" method="post">
    <label>Choix du nom de la filière : </label>
    <input type="text" name="newMajor" required/>
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
