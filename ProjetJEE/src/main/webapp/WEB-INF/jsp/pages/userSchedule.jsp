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
    <title>Emploi du temps</title>
</head>
<body>
<h1>Emploi du temps : </h1>
<%
    Integer userId = (Integer) session.getAttribute("user");
    if(userId == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    String role = RoleDAO.getRoleNameById(UserDAO.getUserById(userId).getRoleId());
    boolean isTeacher = "teacher".equals(role);

    Map<LocalDate, List<Lesson>> lessonList = (Map<LocalDate, List<Lesson>>) request.getAttribute("lessonList");

    if(lessonList == null || lessonList.isEmpty()) {
%>
<p>Il n'y a pas de cours dans l'emploi du temps</p>
<%
    } else {
        Map<LocalDate, List<Lesson>> pastLessons = new TreeMap<>();
        Map<LocalDate, List<Lesson>> futureLessons = new TreeMap<>();
        LocalDate today = LocalDate.now();

        for (Map.Entry<LocalDate, List<Lesson>> entry : lessonList.entrySet()) {
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
            <p>Pas de cours passés</p>
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
                    <% if(!isTeacher) {
                    %>
                        <th>Nom prénom de l'enseignant</th>
                    <%
                    }
                    %>
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
                        <%= CourseDAO.getCourseName(lesson.getCourseId()) %>
                    </td>
                    <% if(!isTeacher) {
                    %>
                        <td><%= UserDAO.getUserById(lesson.getTeacherId()).getUserLastName() + " " + UserDAO.getUserById(lesson.getTeacherId()).getUserName() %></td>
                    <%
                        }
                    %>
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
            if(futureLessons == null || futureLessons.isEmpty()) {
        %>
            <p>Pas de cours à venir</p>
        <%
            } else {
                for(Map.Entry<LocalDate, List<Lesson>> entry : futureLessons.entrySet()) {
                    LocalDate day = entry.getKey();
                    List<Lesson> lessons = entry.getValue();
        %>

        <h2><%= day.format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy")) %></h2>

        <table border="1">
            <tr>
                <th>Nom du cours</th>
                <% if(!isTeacher) {
                %>
                <th>Nom prénom de l'enseignant</th>
                <%
                    }
                %>
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
                    <%= CourseDAO.getCourseName(lesson.getCourseId()) %>
                </td>
                <% if(!isTeacher) {
                %>
                <td><%= UserDAO.getUserById(lesson.getTeacherId()).getUserLastName() + " " + UserDAO.getUserById(lesson.getTeacherId()).getUserName() %></td>
                <%
                    }
                %>
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
