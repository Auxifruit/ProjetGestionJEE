<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 10/11/2024
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.dao.CourseDAO" %>
<%@ page import="com.example.projetjee.model.entities.*" %>
<br>
<head>
    <title>Création séance</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<script src="${pageContext.request.contextPath}/js/showTable.js"></script>
<body>
<jsp:include page="/elements/sidebar.jsp" />

<div>
  <h1>Création d'une séance</h1>
  <%
    Integer userId = (Integer) session.getAttribute("user");
    if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
      response.sendRedirect("index.jsp");
      return;
    }

    List<Course> coursesList = (List<Course>) request.getAttribute("courses");

    if (coursesList == null || coursesList.isEmpty()) {
  %>
  <h2>Pas de cours pour faire une séance</h2>
  <%
  } else {
  %>
  <%
    List<Lesson> lessonList = (List<Lesson>) request.getAttribute("lessons");
    if (lessonList == null || lessonList.isEmpty()) {
  %>
  <h2>Pas de séance pour l'instant</h2>
  <%
  } else {
  %>
  <div id="OldInfos">
    <label>Séance existante :</label>
    <button onclick="toggleTable()">Afficher/Masquer le tableau</button></br></br>
    <table border="1" id="existingTable" style="display: table">
      <tr>
        <th>Nom du cours</th>
        <th>Nom et prénom du professeur</th>
        <th>Date de début</th>
        <th>Date de fin</th>
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
      </tr>
      <%
        }
      %>
    </table>
  <%
    }
  %>
  </div>
  <form action="lessonCreation-servlet" method="post">
  <label>Choix du cours : </label>
  <select name="course">
    <%
      for (Course course : coursesList) {
    %>
    <option value=<%= course.getCourseId() %>><%= course.getCourseName()%></option>
    <%
      }
    %>
  </select>
  </br></br>
  <label>Date de début : </label>
  <input name="startDate" type="datetime-local" required/>
  </br>
  <label>Date de fin : </label>
  <input name="endDate" type="datetime-local" required/>
  </br></br>
  <%
    List<Teacher> teacherList = (List<Teacher>) request.getAttribute("teachers");

    if (teacherList == null || teacherList.isEmpty()) {
  %>
  <h3>Pas de professeur pour faire le cours</h3>
  <%
  } else {
  %>
  <label>Choix du professeur : </label>
  <select name="teacher">
    <%
      for (Teacher teacher : teacherList) {
        Users user = UserDAO.getUserById(teacher.getTeacherId());

        String teacherLastName = user.getUserLastName();
        String teacherName = user.getUserName();

        if(teacherLastName != null || teacherName != null) {
    %>
    <option value=<%= teacher.getTeacherId() %>><%= teacherLastName + " " + teacherName %></option>
    <%
        }
      }
    %>
  </select>
    <% String messageErreur = (String) request.getAttribute("erreur");
      if(messageErreur != null && !messageErreur.isEmpty()) {
    %>
    <p style='color: red'><%= messageErreur %></p>
    <%
          }
        }
      }
    %>
  <button type="submit" onclick="confirmCreate(event)">Valider</button>
  </form>
</div>
</body>
<script>
  function confirmCreate(event) {
    const confirmation = confirm("Êtes-vous sûr de vouloir créer la séance ?");

    if (!confirmation) {
      event.preventDefault();
    }
  }
</script>
</html>
