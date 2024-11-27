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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="/elements/sidebar.jsp" />

<div>
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
    <div id="OldInfos">
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
    </div>
    <form method="post">
        <h3>Nouvelles informations</h3>
        <label>Nouveau nom : </label>
        <input type="text" name="userNewLastName">

        <label>Nouveau prénom : </label>
        <input type="text" name="userNewName">

        <label>Nouveau email : </label>
        <input type="text" name="userNewEmail">

        <label>Nouvelle date de naissance : </label>
        <input type="date" name="userNewBirthdate">

        <%
            if(role.equals(Role.student)) {
                List<Classes> classesList = ClasseDAO.getAllClasses();
                List<Major> majorList = MajorDAO.getAllMajor();
        %>
            <label>Nouvelle classe : </label>
            <select name="userNewClassesId">
                <option value="">Ne pas changer la classe</option>
                <%
                    for(Classes classe : classesList) {
                %>
                <option value="<%= classe.getClassId() %>"><%= classe.getClassName() %></option>
                <%
                    }
                %>
            </select>

            <label>Nouvelle filière : </label>
            <select name="userNewMajorId">
                <option value="">Ne pas changer la filière</option>
                <%
                    for(Major major : majorList) {
                %>
                <option value="<%= major.getMajorId() %>"><%= major.getMajorName() %></option>
                <%
                    }
                %>
            </select>

        <%
            }
        %>

        <label>Nouveaux rôle : </label>
        <select name="userNewRole">
            <option value="">Ne pas changer le rôle</option>
            <option value=<%= Role.student %>>Étudiants</option>
            <option value="<%= Role.teacher %>">Enseignants</option>
            <option value="<%= Role.administrator %>">Administrateurs</option>
        </select>

        <input name="userId" value="<%= user.getUserId() %>" style="display: none">
        <%
            }
        %>
        <% String messageErreur = (String) request.getAttribute("erreur");
            if (messageErreur != null && !messageErreur.isEmpty()) {
        %>
        <p style='color: red'><%= messageErreur %></p>
        <%
            }
        %>

        <button type="submit" onclick="confirmModify(event)">Modifier</button>
    </form>
</div>
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
