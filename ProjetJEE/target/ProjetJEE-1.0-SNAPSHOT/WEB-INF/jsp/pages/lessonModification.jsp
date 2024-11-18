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
<h1>Modification d'une séance</h1>
<%
  Seance lesson = (Seance) request.getAttribute("lesson");

  if (lesson == null) {
%>
<h2>Pas de séance à modifier</h2>
<%
} else {
%>
<h3>Anciennes informations :</h3>
<p>Ancien cours :
<%
  if(lesson.getIdCours() == null || lesson.getIdCours() <= 0) {
%>
Il n'y a pas de cours associé à la séance</p>
<%
  } else {
%>
<%= CourseDAO.getCourseName(lesson.getIdCours()) %></p>
<%
  }
%>
<p>Ancien Enseignant :
<%
  if(lesson.getIdEnseignant() == null || lesson.getIdEnseignant() <= 0) {
%>
Il n'y a pas d'enseignant associé à la séance</p>
<%
} else {
%>
<%= " " + UserDAO.getLastNameById(lesson.getIdEnseignant()) + " " + UserDAO.getNameById(lesson.getIdEnseignant()) %></p>
<%
  }
%>
<p>Ancienne date de début : <%= lesson.getDateDebutSeance() %></p>
<p>Ancienne date de fin : <%= lesson.getDateFinSeance() %></p>
</br>
<form action="lessonModification-servlet" method="post">
<label>Choix du nouveau cours : </label>
  <%
    List<Cours> coursesList = (List<Cours>) request.getAttribute("courses");

    if (coursesList == null || coursesList.isEmpty()) {
  %>
  <h3>Pas de séacence à selectionner</h3>
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
<input name="lessonId" value="<%= lesson.getIdSeance() %>" style="visibility: hidden">
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
