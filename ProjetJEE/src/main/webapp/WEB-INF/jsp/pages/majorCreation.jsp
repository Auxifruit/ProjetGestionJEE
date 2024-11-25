<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Major" %>
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
    <title>Création de filière</title>
</head>
<script src="${pageContext.request.contextPath}/js/showTable.js"></script>
<body>
<h1>Création d'une nouvelle filière</h1>
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
<h3>Filière existante : </h3>
<button onclick="toggleTable()">Afficher/Masquer le tableau</button></br>
<table border="1" id="existingTable" style="display: table">
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
    <button type="submit" onclick="confirmCreate(event)">Créer</button>
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
<script>
    function confirmCreate(event) {
        const confirmation = confirm("Êtes-vous sûr de vouloir créer la filière ?");

        if (!confirmation) {
            event.preventDefault();
        }
    }
</script>
</html>
