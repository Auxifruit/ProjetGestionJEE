<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 10/11/2024
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Teacher" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.entities.Course" %>
<%@ page import="com.example.projetjee.model.entities.Lesson" %>
<%@ page import="com.example.projetjee.model.dao.CourseDAO" %>
<%@ page import="com.example.projetjee.model.dao.RoleDAO" %>
<%@ page import="com.example.projetjee.model.entities.Users" %>
<br>
<head>
    <title>Modification séance</title>
</head>
<body>
<h1>Modification d'une séance</h1>
<%
  Integer userId = (Integer) session.getAttribute("user");
  if(userId == null || !"administrator".equals(RoleDAO.getRoleNameById(UserDAO.getUserById(userId).getRoleId()))) {
    response.sendRedirect("index.jsp");
    return;
  }

  Lesson lesson = (Lesson) request.getAttribute("lesson");

  if (lesson == null) {
%>
<h2>Pas de séance à modifier</h2>
<%
} else {
%>
<h3>Anciennes informations :</h3>
<p>Ancien cours :
<%
  if(lesson.getCourseId() == null || lesson.getCourseId() <= 0) {
%>
Il n'y a pas de Course associé à la séance</p>
<%
  } else {
%>
<%= CourseDAO.getCourseName(lesson.getCourseId()) %></p>
<%
  }
%>
<p>Ancien enseignant :
<%
  if(lesson.getTeacherId() == null || lesson.getTeacherId() <= 0) {
%>
Il n'y a pas d'Teacher associé à la séance</p>
<%
} else {
%>
<%= " " + UserDAO.getUserById(lesson.getTeacherId()).getUserLastName() + " " + UserDAO.getUserById(lesson.getTeacherId()).getUserName() %></p>
<%
  }
%>
<p>Ancienne date de début : <%= lesson.getLessonStartDate() %></p>
<p>Ancienne date de fin : <%= lesson.getLessonEndDate() %></p>
</br>
<h3>Nouvelles informations :</h3>
<form action="lessonModification-servlet" method="post">
<label>Choix du nouveau cours : </label>
  <%
    List<Course> coursesList = (List<Course>) request.getAttribute("courses");

    if (coursesList == null || coursesList.isEmpty()) {
  %>
  <p>Pas de cours à selectionner</p>
  <%
    }
    else {
  %>
  <select name="newCourseId">
    <option value="">Ne pas modifier le cours</option>
  <%
      for (Course course : coursesList) {
  %>
    <option value=<%= course.getCourseId() %>><%= course.getCourseName()%></option>
  <%
      }
  %>
</select>
  <%
    }
  %>
</br></br>
<label>Choix de la nouvelle date de début et de fin : </label></br>
<label>Nouvelle date de début : </label>
<input name="newStartDate" type="datetime-local"/>
</br>
<label>Nouvelle date de fin : </label>
<input name="newEndDate" type="datetime-local"/>
</br></br>
<%
  List<Teacher> teacherList = (List<Teacher>) request.getAttribute("teachers");

  if (teacherList == null || teacherList.isEmpty()) {
%>
<h3>Pas d'enseignant à selectionner</h3>
<%
} else {
%>
<label>Choix de l'enseignant : </label>
<select name="newTeacherId">
  <option value="">Ne pas modifier l'enseignant</option>
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
<input name="lessonId" value="<%= lesson.getLessonId() %>" style="visibility: hidden">
</br></br>
<button type="submit">Valider</button>
</form>
<% String messageErreur = (String) request.getAttribute("erreur");
  if(messageErreur != null && !messageErreur.isEmpty()) {
%>
<p style='color: red'><%= messageErreur %></p></br>
<%
      }
    }
  }
%>
</body>
</html>
