<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.dao.CourseDAO" %>
<%@ page import="com.example.projetjee.model.entities.*" %>
<%@ page import="com.example.projetjee.model.dao.ClasseDAO" %><%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 12/11/2024
  Time: 19:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Création d'utilisateur</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<script src="${pageContext.request.contextPath}/js/showTable.js"></script>
<body>
<jsp:include page="/elements/sidebar.jsp" />
<div>
    <h1>Création d'un nouveau utilisateur</h1>
    <div id="OldInfos">
        <h3>Utilisateurs existants : </h3>
    <%
        Integer userId = (Integer) session.getAttribute("user");
        if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
            response.sendRedirect("index.jsp");
            return;
        }

        List<Users> userList = (List<Users>) request.getAttribute("users");

        if (userList == null || userList.isEmpty()) {
    %>
    <p>Il n'y a pas encore d'utilisateur.</p>
    <%
    } else {
    %>
        <button onclick="toggleTable()">Afficher/Masquer le tableau</button></br></br>
        <table border="1" id="existingTable" style="display: table">
            <tr>
                <th>Nom de l'utilisateur</th>
                <th>Prenom de l'utilisateur</th>
                <th>Email de l'utilisateur</th>
                <th>Date de naissance de l'utilisateur</th>
                <th>Rôle de l'utilisateur</th>
            </tr>
            <%
                for (Users user : userList) {
            %>
            <tr>
                <td><%= user.getUserLastName() %></td>
                <td><%= user.getUserName() %></td>
                <td><%= user.getUserEmail() %></td>
                <td><%= user.getUserBirthdate() %></td>
                <td><%= user.getUserRole() %></td>
            </tr>
            <%
                }
            %>
        </table>
        <%
            }
        %>
    </div>
    <form action="userCreation-servlet" method="post">
        <h3>Nouveau utilisateur :</h3>
        <label>Nom de l'utilisateur : </label>
        <input type="text" name="newUserLastName" required/>

        <label>Prénom de l'utilisateur : </label>
        <input type="text" name="newUserName" required/>

        <label>Email de l'utilisateur : </label>
        <input type="text" name="newUserEmail" required/>

        <label>Mot de passe de l'utilisateur : </label>
        <input type="text" name="newUserPassword" required/>

        <label>Date de naissance de l'utilisateur : </label>
        <input type="date" name="newUserBirthdate" required/>

        <label>Role de l'utilisateur : </label>
        <select name="newUserRole" required>
            <option value="<%= Role.student %>">Étudiant</option>
            <option value="<%= Role.teacher %>">Enseignant</option>
            <option value="<%= Role.administrator %>">Administrateur</option>
        </select>

        <% String messageErreur = (String) request.getAttribute("erreur");
            if (messageErreur != null && !messageErreur.isEmpty()) {
        %>
        <p style='color: red'><%= messageErreur %></p>
        <%
            }
        %>
        <button type="submit" onclick="confirmCreate(event)">Créer</button>
    </form>
</div>
</body>
<script>
    function confirmCreate(event) {
        const confirmation = confirm("Êtes-vous sûr de vouloir créer l'utilisateur ?");

        if (!confirmation) {
            event.preventDefault();
        }
    }
</script>
</html>