<%@ page import="com.example.projetjee.model.entities.Lesson" %>
<%@ page import="com.example.projetjee.model.entities.Classes" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.dao.CourseDAO" %>
<%@ page import="com.example.projetjee.model.dao.LessonClassesDAO" %>
<%@ page import="com.example.projetjee.model.entities.Role" %>
<%--
Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 15/11/2024
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Assignation des séances</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div>
    <h1>Assignation des classes à des séances</h1>
    <%
        Integer userId = (Integer) session.getAttribute("user");
        if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
            response.sendRedirect("index.jsp");
            return;
        }

        Lesson lesson = (Lesson) request.getAttribute("lesson");

        if (lesson == null) {
    %>
    <h2>La séance n'existe pas</h2>
    <%
    } else {
    %>
    <div id="OldInfos">
        <h3>Informations de la séance :</h3>
        <p>Cours :
            <%
                if(lesson.getCourseId() == null || lesson.getCourseId() <= 0) {
            %>
            Il n'y a pas de cours associé à la séance</p>
        <%
        } else {
        %>
            <%= CourseDAO.getCourseName(lesson.getCourseId()) %></p>
        <%
            }
        %>
        <p>Enseignant :
            <%
                if(lesson.getTeacherId() == null || lesson.getTeacherId() <= 0) {
            %>
            Il n'y a pas d'enseignant associé à la séance</p>
        <%
        } else {
        %>
            <%= " " + UserDAO.getUserById(lesson.getTeacherId()).getUserLastName() + " " + UserDAO.getUserById(lesson.getTeacherId()).getUserName() %></p>
        <%
            }
        %>
        <p>Date de début : <%= lesson.getLessonStartDate() %></p>
        <p>Date de fin : <%= lesson.getLessonEndDate() %></p>
    </div>
    <form action="lessonClassesAssignation-servlet" method="post">
        <h3>Classe(s) disponible(s) : </h3>
        <%
            List<Classes> availableClass = (List<Classes>) request.getAttribute("availableClasses");

            if(availableClass == null || availableClass.isEmpty()) {
        %>
        <p>Aucune classe n'est disponible pour la séance</p>
        <%
            } else {
        %>
        <table border="1">
            <tr>
                <th>Nom de la classe</th>
                <th>Selection</th>
            </tr>
            <%
                for(Classes classes : availableClass) {
            %>
            <tr>
                <td><%= classes.getClassName() %></td>
                <td><input type="radio" name="classId" value="<%= classes.getClassId() %>"></td>
            </tr>
            <%
                }
            %>
        </table>
            <input type="text" name="lessonId" value="<%= lesson.getLessonId() %>" style="display: none">
            </br>
            <button type="submit" onclick="confirmAction(event, 'assign')">Assigner</button>
        <%
            }
        %>
    </form>
    <form action="lessonClassesUnassignation-servlet" method="post">
        <h3>Classe(s) participantes(s) : </h3>
        <%

            List<Classes> participatingClass = LessonClassesDAO.getLessonClasses(lesson.getLessonId());
            if(participatingClass == null || participatingClass.isEmpty()) {
        %>
        <p>Aucune classe ne participe à la séance</p>
        <%
            } else {
        %>
        <table border="1">
            <tr>
                <th>Nom de la classe</th>
                <th>Selection</th>
            </tr>
            <%
                for(Classes classes : participatingClass) {
            %>
            <tr>
                <td><%= classes.getClassName() %></td>
                <td><input type="radio" name="classId" value="<%= classes.getClassId() %>"></td>
            </tr>
            <%
                }
            %>
        </table>
        <input type="text" name="lessonId" value="<%= lesson.getLessonId() %>" style="display: none">

        <% String messageErreur = (String) request.getAttribute("erreur");
            if(messageErreur != null && !messageErreur.isEmpty()) {
        %>
        <p style='color: red'><%= messageErreur %></p>
        <%
                }
        %>

        <button type="submit" onclick="confirmAction(event, 'unassign')">Désassigner</button>
        <%
            }
        %>
    </form>
    <%
        }
    %>
</div>
</body>
<script>
    function confirmAction(event, action) {
        let confirmationMessage = '';

        if (action === 'assign') {
            confirmationMessage = "Êtes-vous sûr de vouloir assigner cette classe de cette séance ?";
        } else if (action === 'unassign') {
            confirmationMessage = "Êtes-vous sûr de vouloir désassigner cette classe de cette séance ?";
        } else {
            confirmationMessage = "Êtes-vous sûr de vouloir effectuer cette action ?";
        }

        const confirmation = confirm(confirmationMessage);

        if (!confirmation) {
            event.preventDefault(); // Annule l'envoi du formulaire si l'utilisateur annule
        }
    }
</script>
</html>
