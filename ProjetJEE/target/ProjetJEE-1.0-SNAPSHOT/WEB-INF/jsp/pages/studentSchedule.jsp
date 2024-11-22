<%@ page import="com.example.projetjee.model.entities.Lesson" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.example.projetjee.model.entities.Classes" %>
<%@ page import="com.example.projetjee.model.dao.*" %>
<%@ page import="java.util.TreeMap" %><%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 19/11/2024
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Emplois du temps étudiant</title>
</head>
<body>
<h1>Emplois du temps : </h1>
<%
    Map<LocalDate, List<Lesson>> studentLesson = (Map<LocalDate, List<Lesson>>) request.getAttribute("studentLesson");

    if(studentLesson == null || studentLesson.isEmpty()) {
%>
<p>L'étudiant n'a pas de cours</p>
<%
    } else {
        Map<LocalDate, List<Lesson>> pastLessons = new TreeMap<>();
        Map<LocalDate, List<Lesson>> futureLessons = new TreeMap<>();
        LocalDate today = LocalDate.now();

        for (Map.Entry<LocalDate, List<Lesson>> entry : studentLesson.entrySet()) {
            if (entry.getKey().isBefore(today)) {
                pastLessons.put(entry.getKey(), entry.getValue());
            } else {
                futureLessons.put(entry.getKey(), entry.getValue());
            }
        }
%>
        <div style="display: none" id="pastCoursesTable">
            <%
                if(pastLessons == null || pastLessons.isEmpty()) {
            %>
            <p>L'étudiant n'a pas de cours passé</p>
            <%
                } else {
                    for(Map.Entry<LocalDate, List<Lesson>> entry : pastLessons.entrySet()) {
                        LocalDate day = entry.getKey();
                        List<Lesson> lessons = entry.getValue();
            %>
            <h2><%= day.format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy")) %></h2>

            <table border="1">
                <tr>
                    <th>Nom du cours</th>
                    <th>Nom prénom de l'enseignant</th>
                    <th>Heure de début</th>
                    <th>Heure de fin</th>
                    <th>Classe participante</th>
                </tr>

                <%
                    for(Lesson lesson : lessons) {
                        LocalDateTime startDate = lesson.getLessonStartDate().toLocalDateTime();
                        LocalDateTime endDate = lesson.getLessonEndDate().toLocalDateTime();
                        List<Classes> participantClass = LessonClassesDAO.getLessonClasses(lesson.getLessonId());
                %>
                <tr>
                    <td>
                        <%
                            if(participantClass.size() > 1 ) {
                        %>
                        CM
                        <%
                        } else {
                        %>
                        TD
                        <%
                            }
                        %>
                        <%= CourseDAO.getCourseName(lesson.getCourseId()) %></td>
                    <td><%= UserDAO.getLastNameById(lesson.getTeacherId()) + " " + UserDAO.getNameById(lesson.getTeacherId()) %></td>
                    <td><%= startDate.format(DateTimeFormatter.ofPattern("HH:mm")) %></td>
                    <td><%= endDate.format(DateTimeFormatter.ofPattern("HH:mm")) %></td>
                    <td>
                        <%
                            if(participantClass == null || participantClass.isEmpty()) {
                        %>
                        <p>Aucune classe ne participe au cours</p>
                        <%
                        } else {
                            for(Classes classe : participantClass) {
                        %>
                        <%= classe.getClassName() + " "%>
                        <%
                                }
                            }
                        %>
                    </td>
                </tr>
                <%
                    }
                %>

            </table>
            <%
                }
                }
            %>
        </div>
        </br>
        <button onclick="toggleDisplay()">Afficher ou non les cours passés</button>

        <%
        for(Map.Entry<LocalDate, List<Lesson>> entry : futureLessons.entrySet()) {
            LocalDate day = entry.getKey();
            List<Lesson> lessons = entry.getValue();
        %>

        <h2><%= day.format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy")) %></h2>

        <table border="1">
            <tr>
                <th>Nom du cours</th>
                <th>Nom prénom de l'enseignant</th>
                <th>Heure de début</th>
                <th>Heure de fin</th>
                <th>Classe participante</th>
            </tr>

            <%
                for(Lesson lesson : lessons) {
                    LocalDateTime startDate = lesson.getLessonStartDate().toLocalDateTime();
                    LocalDateTime endDate = lesson.getLessonEndDate().toLocalDateTime();
                    List<Classes> participantClass = LessonClassesDAO.getLessonClasses(lesson.getLessonId());
            %>
            <tr>
                <td>
                    <%
                        if(participantClass.size() > 1 ) {
                    %>
                    CM
                    <%
                        } else {
                    %>
                    TD
                    <%
                        }
                    %>
                    <%= CourseDAO.getCourseName(lesson.getCourseId()) %></td>
                <td><%= UserDAO.getLastNameById(lesson.getTeacherId()) + " " + UserDAO.getNameById(lesson.getTeacherId()) %></td>
                <td><%= startDate.format(DateTimeFormatter.ofPattern("HH:mm")) %></td>
                <td><%= endDate.format(DateTimeFormatter.ofPattern("HH:mm")) %></td>
                <td>
                    <%
                        if(participantClass == null || participantClass.isEmpty()) {
                    %>
                    <p>Aucune classe ne participe au cours</p>
                    <%
                        } else {
                            for(Classes classe : participantClass) {
                    %>
                    <%= classe.getClassName() + " "%>
                    <%
                            }
                        }
                    %>
                </td>
            </tr>
            <%
                }
            %>

        </table>
            <%
        }
    }
%>

</body>
<script>
    function toggleDisplay() {
        const table = document.getElementById('pastCoursesTable');
        if (table.style.display === 'none') {
            table.style.display = 'table';
        } else {
            table.style.display = 'none';
        }
    }
</script>
</html>