<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.dao.StudentDAO" %>
<%@ page import="com.example.projetjee.model.dao.ClasseDAO" %>
<%@ page import="com.example.projetjee.model.dao.MajorDAO" %>
<%@ page import="com.example.projetjee.model.entities.*" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 12/11/2024
  Time: 19:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modification d'utilisateur</title>
</head>
<body>
<h1>Modification d'un utilisateur</h1>
<%
    Integer userId = (Integer) session.getAttribute("user");
    if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
        response.sendRedirect("index.jsp");
        return;
    }

    Users user = (Users) request.getAttribute("user");
    Role role = user.getUserRole();

    if (user == null) {
%>
<p>La matière n'existe pas</p>
<%
} else {
%>
<h3>Anciennes informations</h3>
<p>Ancien nom : <%= user.getUserLastName() %></p>
<p>Ancien prénom : <%= user.getUserName() %></p>
<p>Ancien email : <%= user.getUserEmail() %></p>
<p>Ancienne date de naissance : <%= user.getUserBirthdate() %></p>
<%
    if(role.equals(Role.student)) {
        Student student = StudentDAO.getStudentById(user.getUserId());

        Integer studentClassId = student.getClassId();
        boolean hasClassId = student.getClassId() != null;
        Integer studentMajorId = student.getMajorId();
        boolean hasMajorId = student.getMajorId() != null;
    %>
    <p>Ancienne classe :
    <%
        if(hasClassId) {
    %>
     <%= ClasseDAO.getClasseById(studentClassId).getClassName() %>
    <%
    } else {
    %>
    Sans classe
    <%
        }
    %>
    </p>
    <p>Ancienne filière :
    <%
        if(hasMajorId) {
    %>
    <%= MajorDAO.getMajorById(studentMajorId).getMajorName() %>
    <%
    } else {
    %>
    Sans filière
    <%
        }
    %>
    </p>
    <p>Ancien rôle : <%= user.getUserRole() %></p>
<%
    }
%>

<h3>Nouvelles informations</h3>
<form method="post">
    <label>Nouveau nom : </label></br>
    <input type="text" name="userNewLastName"></br></br>

    <label>Nouveau prénom : </label></br>
    <input type="text" name="userNewName"></br></br>

    <label>Nouveau email : </label></br>
    <input type="text" name="userNewEmail"></br></br>

    <label>Nouvelle date de naissance : </label></br>
    <input type="date" name="userNewBirthdate"></br></br>

    <%
        if(role.equals(Role.student)) {
            List<Classes> classesList = ClasseDAO.getAllClasses();
            List<Major> majorList = MajorDAO.getAllMajor();
    %>
        <label>Nouvelle classe : </label></br>
        <select name="userNewClassesId">
            <option value="">Ne pas changer la classe</option>
            <%
                for(Classes classe : classesList) {
            %>
            <option value="<%= classe.getClassId() %>"><%= classe.getClassName() %></option>
            <%
                }
            %>
        </select></br></br>

        <label>Nouvelle filière : </label></br>
        <select name="userNewMajorId">
            <option value="">Ne pas changer la filière</option>
            <%
                for(Major major : majorList) {
            %>
            <option value="<%= major.getMajorId() %>"><%= major.getMajorName() %></option>
            <%
                }
            %>
        </select></br></br>

    <%
        }
    %>

    <label>Nouveaux rôle : </label></br>
    <select name="userNewRole">
        <option value="">Ne pas changer le rôle</option>
        <option value=<%= Role.student %>>Étudiants</option>
        <option value="<%= Role.teacher %>">Enseignants</option>
        <option value="<%= Role.administrator %>">Administrateurs</option>
    </select>

    <input name="userId" value="<%= user.getUserId() %>" style="visibility: hidden">

    </br></br>
    <button type="submit" onclick="confirmModify(event)">Modifier</button>
</form>
<%
    }
%>
<% String messageErreur = (String) request.getAttribute("erreur");
    if (messageErreur != null && !messageErreur.isEmpty()) {
%>
<p style='color: red'><%= messageErreur %></p></br>
<%
    }
%>
</body>
<script>
    function confirmModify(event) {
        const confirmation = confirm("Êtes-vous sûr de vouloir modifier l'utilisateur ?");

        if (!confirmation) {
            event.preventDefault();
        }
    }
</script>
</html>
