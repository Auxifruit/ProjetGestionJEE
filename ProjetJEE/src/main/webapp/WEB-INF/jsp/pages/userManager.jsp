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
<%@ page import="com.example.projetjee.model.dao.ClasseDAO" %>
<%@ page import="com.example.projetjee.model.dao.StudentDAO" %>
<%@ page import="com.example.projetjee.model.entities.Student" %>
<%@ page import="com.example.projetjee.model.dao.MajorDAO" %>
<html>
<head>
    <title>Liste utilisateurs</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<script src="${pageContext.request.contextPath}/js/filterTable.js"></script>
<body>
<jsp:include page="/elements/sidebar.jsp" />

<div>
    <h1>Liste des utilisateurs</h1>
    <%
        Integer userId = (Integer) session.getAttribute("user");
        if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
            response.sendRedirect("index.jsp");
            return;
        }

        List<Users> userList = (List<Users>) request.getAttribute("users");

        String selectedRole = request.getParameter("roleFilter");
        Role roleFilter = null;
        if(selectedRole != null && !selectedRole.isEmpty()) {
            roleFilter = Role.valueOf(selectedRole);
        }

        if(userList == null || userList.isEmpty()) {
    %>
    <h2>Il n'y a pas d'utilisateur</h2>
    <%
    } else {
    %>
    <label for="searchInput">Rechercher :</label>
    <input type="text" id="searchInput" onkeyup="filterTable()" placeholder="Recherche">
    </br></br>
    <form action="userManager-servlet" method="get" >
        <label><b>Filtrer par : </b></label>
        <select name="roleFilter">
            <option value=<%= Role.student %> <%= roleFilter != null && roleFilter.equals(Role.student) ? "selected" : "" %>>Étudiants</option>
            <option value=<%= Role.teacher %> <%= roleFilter != null && roleFilter.equals(Role.teacher) ? "selected" : "" %>>Enseignants</option>
            <option value=<%= Role.administrator %> <%= roleFilter != null && roleFilter.equals(Role.administrator) ? "selected" : "" %>>Administrateurs</option>
        </select>
        <button type="submit">Valider</button>
    </form>
    <%
        boolean isStudentSelected = Role.student.equals(roleFilter);
    %>
    <form method="get">
    <table>
        <tr>
            <th>Nom de l'utilisateur</th>
            <th>Prenom de l'utilisateur</th>
            <th>Email de l'utilisateur</th>
            <th>Date de naissance de l'utilisateur</th>
            <%
                if (isStudentSelected) {
            %>
            <th>Classe</th>
            <th>Filière</th>
            <%
                }
            %>
            <th>Rôle de l'utilisateur</th>
            <th>Selection</th>
        </tr>
        <%
            for (Users user : userList) {
        %>
        <tr>
            <td><%= user.getUserLastName() %></td>
            <td><%= user.getUserName() %></td>
            <td><%= user.getUserEmail() %></td>
            <td><%= user.getUserBirthdate() %></td>
            <%
                if (isStudentSelected) {
                    Student student = StudentDAO.getStudentById(user.getUserId());

                    Integer studentClassId = student.getClassId();
                    Integer studentMajorId = student.getMajorId();
            %>
            <td>
            <%
                    if(studentClassId != null) {
            %>
            <%= ClasseDAO.getClasseById(studentClassId).getClassName() %>
            <%
                    } else {
            %>
                Sans classe
            <%
                }
            %>
            </td>
            <td>
                <%
                    if(studentMajorId != null) {
                %>
                <%= MajorDAO.getMajorById(studentMajorId).getMajorName() %>
                <%
                } else {
                %>
                Sans filière
                <%
                    }
                %>
            </td>
            <%
                }
            %>
            <td><%= user.getUserRole() %></td>
            <td><input type="radio" name="userId" value="<%= user.getUserId() %>" required></td>
        </tr>
        <%
            }
        %>
    </table>
        <% String messageErreur = (String) request.getAttribute("erreur");
            if(messageErreur != null && !messageErreur.isEmpty()) {
        %>
        <p style='color: red'><%= messageErreur %></p>
        <%
                }
            }
        %>
    <button type="submit" formaction="userModification-servlet">Modifier</button>
    <button type="submit" formaction="userDeletion-servlet" onclick="confirmDelete(event)">Supprimer</button>
    </form>
</div>
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
