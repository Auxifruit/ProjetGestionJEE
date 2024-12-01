<%@ page import="com.example.projetjee.model.entities.Lesson" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.example.projetjee.model.entities.Classes" %>
<%@ page import="com.example.projetjee.model.dao.*" %>
<%@ page import="java.util.TreeMap" %>
<%@ page import="com.example.projetjee.model.entities.Role" %>
<%@ page import="com.example.projetjee.model.entities.Users" %><%--
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
<jsp:include page="/elements/sidebar.jsp" />

<div>
    <%
        String userIdForm = request.getAttribute("userIdForm").toString();
        Integer userId = (Integer) session.getAttribute("user");

        if(userIdForm == null || userIdForm.isEmpty() || userId == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        Users usersSchedule = UserDAO.getUserById(Integer.parseInt(userIdForm));
        Role role = usersSchedule.getUserRole();
        boolean isTeacher = Role.teacher.equals(role);
    %>
    <h1>Emploi du temps
        <%
            String userLastName= usersSchedule.getUserLastName();
            String userName= usersSchedule.getUserName();

            if((userLastName != null && !userLastName.isEmpty()) && (userName != null && !userName.isEmpty())) {
        %>
        de <%= userName + " " + userLastName %>
        <%
            }
        %>
        : </h1>
    <%
        Map<LocalDate, List<Lesson>> lessonList = (Map<LocalDate, List<Lesson>>) request.getAttribute("lessonList");

        if(lessonList == null || lessonList.isEmpty()) {
    %>
    <h2>L'emploi du temps est vide</h2>
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
                    <th>Heure de début</th>
                    <th>Heure de fin</th>
                    <th>Nom du cours</th>
                    <th>Classe participante</th>
                    <% if(!isTeacher) { %>
                    <th>Nom prénom de l'enseignant</th>
                    <% } %>
                </tr>

                <%
                    for(Lesson lesson : lessons) {
                        LocalDateTime startDate = lesson.getLessonStartDate().toLocalDateTime();
                        LocalDateTime endDate = lesson.getLessonEndDate().toLocalDateTime();
                        List<Classes> participantClass = LessonClassesDAO.getLessonClasses(lesson.getLessonId());
                %>
                <tr>
                    <td><%= startDate.format(DateTimeFormatter.ofPattern("HH:mm")) %></td>
                    <td><%= endDate.format(DateTimeFormatter.ofPattern("HH:mm")) %></td>
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
                        Pas de cours pour cette séance
                        <%
                            }
                        %>
                    </td>
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
                    <% if(!isTeacher) { %>
                    <td>
                        <%
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
        <button onclick="toggleDisplay()">Afficher/Masquer les cours passés</button>
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
                <th>Heure de début</th>
                <th>Heure de fin</th>
                <th>Nom du cours</th>
                <th>Classe participante</th>
                <% if(!isTeacher) { %>
                <th>Nom prénom de l'enseignant</th>
                <% } %>
            </tr>

            <%
                for(Lesson lesson : lessons) {
                    LocalDateTime startDate = lesson.getLessonStartDate().toLocalDateTime();
                    LocalDateTime endDate = lesson.getLessonEndDate().toLocalDateTime();
                    List<Classes> participantClass = LessonClassesDAO.getLessonClasses(lesson.getLessonId());
            %>
            <tr>
                <td><%= startDate.format(DateTimeFormatter.ofPattern("HH:mm")) %></td>
                <td><%= endDate.format(DateTimeFormatter.ofPattern("HH:mm")) %></td>
                <td>
                    <%
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
                </td>
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
                <% if(!isTeacher) { %>
                <td><%= UserDAO.getUserById(lesson.getTeacherId()).getUserLastName() + " " + UserDAO.getUserById(lesson.getTeacherId()).getUserName() %></td>
                <% } %>
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
