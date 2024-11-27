<%@ page import="com.example.projetjee.model.entities.Lesson" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.example.projetjee.model.entities.Classes" %>
<%@ page import="com.example.projetjee.model.dao.*" %>
<%@ page import="java.util.TreeMap" %>
<%@ page import="com.example.projetjee.model.entities.Role" %><%--
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div>
    <h1>Emploi du temps : </h1>
    <%
        Integer userId = (Integer) session.getAttribute("user");
        if(userId == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        Role role = UserDAO.getUserById(userId).getUserRole();
        boolean isTeacher = Role.teacher.equals(role);

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
    <div id="OldInfos">
        <h2>Cours passés :</h2>
        <div style="display: none" id="pastCoursesTable">
            <%
                if(pastLessons == null || pastLessons.isEmpty()) {
            %>
            <h2>Pas de cours passés</h2>
            <%
                } else {
                    for(Map.Entry<LocalDate, List<Lesson>> entry : pastLessons.entrySet()) {
                        LocalDate day = entry.getKey();
                        List<Lesson> lessons = entry.getValue();
            %>
            <h3><%= day.format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy")) %></h3>

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
                            Integer courseId = lesson.getCourseId();
                            if(courseId != null) {
                                if(participantClass.size() > 1) {
                        %>
                        <div style="color: red">CM</div>
                        <%
                                } else {
                        %>
                        <div style="color: blue">TD</div>
                        <%
                                }
                        %>
                            <%= CourseDAO.getCourseName(lesson.getCourseId()) %>
                        <%
                            } else {
                        %>
                            Pas de cours pour cette
                        <%
                            }
                        %>
                    </td>
                    <td>
                    <% if(!isTeacher) {
                            Integer teacherId = lesson.getTeacherId();
                            if(teacherId != null) {
                    %>
                        <%= UserDAO.getUserById(lesson.getTeacherId()).getUserLastName() + " " + UserDAO.getUserById(lesson.getTeacherId()).getUserName() %>
                    <%
                            } else {
                    %>
                        Pas d'enseignant pour cette séance
                    <%
                            }
                        }
                    %>
                    </td>
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
        </div></br></br>
        <button onclick="toggleDisplay()">Afficher ou non les cours passés</button>
    </div>
    <div id="OldInfos">
            <%
                if(futureLessons == null || futureLessons.isEmpty()) {
            %>
                <h2>Pas de cours à venir</h2>
            <%
                } else {
                    for(Map.Entry<LocalDate, List<Lesson>> entry : futureLessons.entrySet()) {
                        LocalDate day = entry.getKey();
                        List<Lesson> lessons = entry.getValue();
            %>

            <h3><%= day.format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy")) %></h3>

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
                        <div style="color: red">CM</div>
                        <%
                            } else {
                        %>
                        <div style="color: blue">TD</div>
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
    <%
        }
    %>
</div>
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
