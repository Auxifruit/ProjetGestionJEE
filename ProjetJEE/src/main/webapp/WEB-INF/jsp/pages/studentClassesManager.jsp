<%@ page import="com.example.projetjee.model.entities.Classes" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Student" %>
<%@ page import="com.example.projetjee.model.dao.*" %>
<%--
Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 15/11/2024
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Assigner des cours</title>
</head>
<body>
<h1>Assignation des séances à des Classs</h1>
<%
    Integer userId = (Integer) session.getAttribute("user");
    if(userId == null || !"administrator".equals(RoleDAO.getRoleNameById(UserDAO.getUserById(userId).getRoleId()))) {
        response.sendRedirect("index.jsp");
        return;
    }

    Classes classe = (Classes) request.getAttribute("classe");

    if (classe == null) {
%>
<h2>La classe n'existe pas</h2>
<%
} else {
%>
<h3>Informations de la classe :</h3>
<p>Nom : <%= classe.getClassName() %> </p>
</br>
<%
    List<Student> availableStudentList = (List<Student>) request.getAttribute("availableStudents");

    if(availableStudentList == null || availableStudentList.isEmpty()) {
%>
<p>Aucun étudiant n'est disponible pour la classe</p>
<%
    } else {
%>
<h3>Étudiant(s) disponible(s) : </h3>
<form action="studentClassesAssignation-servlet" method="post">
<table border="1">
    <tr>
        <th>Nom de l'étudiant</th>
        <th>Prénom de l'étudiant</th>
        <th>Filière de l'étudiant</th>
        <th>Selection</th>
    </tr>
    <%
        for(Student student : availableStudentList) {
    %>
    <tr>
        <td><%= UserDAO.getUserById(student.getStudentId()).getUserLastName()  %></td>
        <td><%= UserDAO.getUserById(student.getStudentId()).getUserName()  %></td>
        <td>
            <% String majorName = null;
                if(student.getMajorId() != null) {
                    majorName = MajorDAO.getMajorById(student.getMajorId()).getMajorName();
                }
                if(majorName == null) {
            %>
            Aucune
            <%
                } else {
            %>
            <%= majorName %>
            <%
                }
            %>
        </td>
        <td><input type="radio" name="studentId" value="<%= student.getStudentId() %>"></td>
    </tr>
    <%
        }
    %>
</table>
    <input type="text" name="classesId" value="<%= classe.getClassId() %>" style="visibility: hidden">
    </br>
    <button type="submit">Assigner</button>
</form>
<%
    }

    List<Student> participatingStudent = StudentDAO.getStudentListFromClassesId(classe.getClassId());
    if(participatingStudent == null || participatingStudent.isEmpty()) {
%>
<p>Aucune étudiant n'est dans la classe</p>
<%
    } else {
%>
<h3>Étudiant(s) participantes(s) : </h3>
<form action="studentClassesUnassignation-servlet" method="post">
    <table border="1">
        <tr>
            <th>Nom de l'étudiant</th>
            <th>Prénom de l'étudiant</th>
            <th>Filière de l'étudiant</th>
            <th>Selection</th>
        </tr>
        <%
            for(Student student : participatingStudent) {
        %>
        <tr>
            <td><%= UserDAO.getUserById(student.getStudentId()).getUserLastName()  %></td>
            <td><%= UserDAO.getUserById(student.getStudentId()).getUserName()  %></td>
            <td>
                <% String majorName = null;
                    if(student.getMajorId() != null) {
                        majorName = MajorDAO.getMajorById(student.getMajorId()).getMajorName();
                    }
                    if(majorName == null) {
                %>
                Aucune
                <%
                } else {
                %>
                <%= majorName %>
                <%
                    }
                %>
            </td>
            <td><input type="radio" name="studentId" value="<%= student.getStudentId() %>"></td>
        </tr>
        <%
            }
        %>
    </table>
    <input type="text" name="classesId" value="<%= classe.getClassId() %>" style="visibility: hidden">
    </br>
    <button type="submit">Désassigner</button>
</form>
<%
    }
%>
<% String messageErreur = (String) request.getAttribute("erreur");
    if(messageErreur != null && !messageErreur.isEmpty()) {
%>
<p style='color: red'><%= messageErreur %></p></br>
<%
    }
}
%>
</body>
</html>