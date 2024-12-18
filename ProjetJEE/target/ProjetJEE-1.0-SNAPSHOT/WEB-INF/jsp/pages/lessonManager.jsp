<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Lesson" %>
<%@ page import="com.example.projetjee.model.dao.CourseDAO" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.entities.Role" %>
<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 15/11/2024
  Time: 19:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestion des séance</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<script src="${pageContext.request.contextPath}/js/filterTable.js"></script>
<body>
<jsp:include page="/elements/sidebar.jsp" />
<div>
    <h1>Liste des séance</h1>
    <%
        Integer userId = (Integer) session.getAttribute("user");
        if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
            response.sendRedirect("index.jsp");
            return;
        }

        List<Lesson> lessonList = (List<Lesson>) request.getAttribute("lessons");

        if (lessonList == null || lessonList.isEmpty()) {
    %>
    <h2>Il n'y a pas encore de séance</h2>
    <%
    } else {
    %>
    <label for="searchInput">Rechercher :</label>
    <input type="text" id="searchInput" onkeyup="filterTable()" placeholder="Recherche"></br></br>
    <form method="get">
        <table border="1">
            <tr>
                <th>Nom du cours</th>
                <th>Nom et prénom du professeur</th>
                <th>Date de début</th>
                <th>Date de fin</th>
                <th>Selection</th>
            </tr>
            <%
                for (Lesson lesson : lessonList) {
                    Integer courseId = lesson.getCourseId();
                    Integer teacherId = lesson.getTeacherId();
            %>
            <tr>
                <td>
                <% if(courseId == null) {
                %>
                    Pas de cours associé
                <%
                    } else {
                %>
                    <%= CourseDAO.getCourseName(lesson.getCourseId()) %>
                <%
                    }
                %>
                </td>
                <td>
                    <% if(teacherId == null) {
                    %>
                    Pas d'enseignant associé
                    <%
                    } else {
                    %>
                    <%= UserDAO.getUserById(lesson.getTeacherId()).getUserLastName() + " " + UserDAO.getUserById(lesson.getTeacherId()).getUserName() %>
                    <%
                        }
                    %>
                </td>
                <td><%= lesson.getLessonStartDate() %></td>
                <td><%= lesson.getLessonEndDate() %></td>
                <td><input type="radio" name="lessonId" value="<%= lesson.getLessonId()%>" required></td>
            </tr>
            <%
                }
            %>
        </table>
        <% String messageErreur = (String) request.getAttribute("erreur");
            if (messageErreur != null && !messageErreur.isEmpty()) {
        %>
        <p style='color: red'><%= messageErreur %></p>
        <%
            }
        %>
        <button type="submit" formaction="lessonModification-servlet">Modifier</button>
        <button type="submit" formaction="lessonDeletion-servlet" onclick="confirmDelete(event)">Supprimer</button>
        <button type="submit" formaction="lessonClassesManager-servlet">Assigner une ou plusieurs classes à la séance</button>
    </form>
    <%
        }
    %>
    <form action="lessonCreation-servlet" method="get">
        <button type="submit">Créer</button>
    </form>
</div>
</body>
<script>
    function confirmDelete(event) {
        const confirmation = confirm("Êtes-vous sûr de vouloir supprimer la séance ?");

        if (!confirmation) {
            event.preventDefault();
        }
    }
</script>
</html>
