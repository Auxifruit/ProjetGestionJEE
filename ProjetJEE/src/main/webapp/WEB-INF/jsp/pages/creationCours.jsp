<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 10/11/2024
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Matiere" %>
<%@ page import="com.example.projetjee.model.dao.SubjectDAO" %>
<br>
<head>
    <title>Création cours</title>
</head>
<%
  List<Matiere> subjectList = (List<Matiere>) request.getAttribute("subjects");
%>
<%
  if (subjectList == null || subjectList.isEmpty()) {
%>
<h2>Pas de matière pour faire un cours</h2>
<%
} else {
%>
<h1>Création d'un cours</h1>
<label>Choix de la matière : </label>
<select>
  <%
    for (Matiere subject : subjectList) {
  %>
  <option><%= subject.getNomMatière() %></option>
  <%
    }
    }
  %>
</select>
</br></br>
<label>Choix de la date et de l'heure : </label>
<input type="datetime-local" />
</br></br>
<label>Choix du professeur : </label>
<select>
  <option>Ouga Baba</option>
  <option>Popo Toto</option>
  <option>Bidi Bada</option>
</select>
</br></br>
<button>Valider</button>
</body>
</html>
