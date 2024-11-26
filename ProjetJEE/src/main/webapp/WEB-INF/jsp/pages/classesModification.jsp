<%@ page import="com.example.projetjee.model.entities.Classes" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.entities.Role" %><%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 12/11/2024
  Time: 19:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modification de filière</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div>
    <h1>Modification d'une classe</h1>
    <div id="OldInfos">
        <%
            Integer userId = (Integer) session.getAttribute("user");
            if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
                response.sendRedirect("index.jsp");
                return;
            }

            Classes classes = (Classes) request.getAttribute("classe");

            if (classes == null) {
        %>
        <p>La classe n'existe pas</p>
        <%
        } else {
        %>

        <h3>Ancienne information</h3>
        <p>Ancien nom de la classe : <%= classes.getClassName() %></p>
    </div>
    <form action="classesModification-servlet" method="post">
        <h3>Nouvelle information</h3>
        <label>Nouveau nom de la classe : </label>
        <input type="text" name="classesNewName" required>
        <input name="classesId" value="<%= classes.getClassId() %>" style="display: none">

        <% String messageErreur = (String) request.getAttribute("erreur");
            if (messageErreur != null && !messageErreur.isEmpty()) {
        %>
        <p style='color: red'><%= messageErreur %></p>
        <%
            }
        %>
        <button type="submit" onclick="confirmModify(event)">Modifier</button>
    </form>
    <%
        }
    %>
</div>
</body>
<script>
    function confirmModify(event) {
        const confirmation = confirm("Êtes-vous sûr de vouloir modifier la classe ?");

        if (!confirmation) {
            event.preventDefault();
        }
    }
</script>
</html>
