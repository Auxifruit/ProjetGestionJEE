<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 07/11/2024
  Time: 15:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>List of Students</title>
</head>
<body>
<h1>List of Students</h1>

<%
    // Retrieve the list of students from the request attribute
    List<models.Etudiant> students = (List<models.Etudiant>) request.getAttribute("students");
%>

<%
    if (students == null || students.isEmpty()) {
%>
<h2>No students available</h2>
<%
} else {
%>
<table border="1px">
    <tr>
        <th>Prénom</th>
        <th>Nom</th>
        <th>Email</th>
    </tr>
    <%
        // Loop through the students and display their details
        for (models.Etudiant student : students) {
    %>
    <tr>
        <td><%= student.getPrénomEtudiant() %></td>
        <td><%= student.getNomEtudiant() %></td>
        <td><%= student.getEmailEtudiant() %></td>
    </tr>
    <%
        }
    %>
</table>
<%
    }
%>
</body>
</html>
