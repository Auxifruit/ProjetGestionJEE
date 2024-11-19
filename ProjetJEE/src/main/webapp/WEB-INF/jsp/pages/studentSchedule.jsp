<%@ page import="com.example.projetjee.model.entities.Lesson" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.example.projetjee.model.entities.Classes" %>
<%@ page import="com.example.projetjee.model.dao.*" %><%--
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

    for(Map.Entry<LocalDate, List<Lesson>> entry : studentLesson.entrySet()) {
    LocalDate day = entry.getKey();
    List<Lesson> lessons = entry.getValue();
        %>

        <h2><%= day.format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy")) %></h2>

        <table border="1">
            <tr>
                <td>Nom du cours</td>
                <td>Nom prénom de l'enseignant</td>
                <td>Heure de début</td>
                <td>Heure de fin</td>
                <td>Classe participante</td>
            </tr>

            <%
                for(Lesson lesson : lessons) {
                    LocalDateTime startDate = lesson.getLessonStartDate().toLocalDateTime();
                    LocalDateTime endDate = lesson.getLessonEndDate().toLocalDateTime();
                    List<Classes> participantClass = LessonClassesDAO.getLessonClasses(lesson.getLessonId());
            %>
            <tr>
                <td><%= CourseDAO.getCourseName(lesson.getCourseId()) %></td>
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
</html>
