<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 10/11/2024
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Enseignant" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.entities.Cours" %>
<%@ page import="com.example.projetjee.model.entities.Seance" %>
<%@ page import="com.example.projetjee.model.dao.CourseDAO" %>
<br>
<head>
    <title>Modification séance</title>
</head>
<body>
<%
  List<Seance> lessonList = (List<Seance>) request.getAttribute("lessons");

  if (lessonList == null || lessonList.isEmpty()) {
%>
<h2>Pas de séance à modifier</h2>
<%
} else {
%>
<h1>Création d'une séance</h1>
<label>Séance existante :</label>
</br>
<form action="lessonModification-servlet" method="post">
<table border="1">
  <tr>
    <th>Nom du cours</th>
    <th>Nom et prénom du professeur</th>
    <th>Date de début</th>
    <th>Date de fin</th>
    <th>Selection</th>
  </tr>
  <%
    for (Seance lesson : lessonList) {
  %>
  <tr>
    <td><%= CourseDAO.getCourseName(lesson.getIdCours()) %></td>
    <td><%= UserDAO.getLastNameById(lesson.getIdEnseignant()) + " " + UserDAO.getNameById(lesson.getIdEnseignant()) %></td>
    <td><%= lesson.getDateDebutSeance() %></td>
    <td><%= lesson.getDateFinSeance() %></td>
    <td><input type="radio" name="lessonId" value="<%= lesson.getIdSeance() %>" required></td>
  </tr>
  <%
    }
  %>
</table>
</br>
<label>Choix du nouveau cours : </label>
  <%
    List<Cours> coursesList = (List<Cours>) request.getAttribute("courses");

    if (coursesList == null || coursesList.isEmpty()) {
  %>
  <h3>Pas de cours à selectionner</h3>
  <%
    }
    else {
  %>
  <select name="newCourseId">
    <option value="">Ne pas modifier le cours</option>
  <%
      for (Cours course : coursesList) {
  %>
    <option value=<%= course.getIdCours() %>><%= course.getNomCours()%></option>
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
  List<Enseignant> teacherList = (List<Enseignant>) request.getAttribute("teachers");

  if (teacherList == null || teacherList.isEmpty()) {
%>
<h3>Pas de professeur à selectionner</h3>
<%
} else {
%>
<label>Choix du enseignant : </label>
<select name="newTeacherId">
  <option value="">Ne pas modifier l'enseignant</option>
  <%
    for (Enseignant teacher : teacherList) {
      String teacherLastName = UserDAO.getLastNameById(teacher.getIdEnseignant());
      String teacherName = UserDAO.getNameById(teacher.getIdEnseignant());

      if(teacherLastName != null || teacherName != null) {
  %>
  <option value=<%= teacher.getIdEnseignant() %>><%= teacherLastName + " " + teacherName %></option>
  <%
      }
    }
  %>
</select>
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
