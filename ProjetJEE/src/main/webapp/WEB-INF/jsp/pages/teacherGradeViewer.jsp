<%--
  Created by IntelliJ IDEA.
  User: Amaury
  Date: 15/11/2024
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Users" %>
<%@ page import="com.example.projetjee.model.entities.Course" %>
<%@ page import="com.example.projetjee.model.entities.Classes" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.entities.Grade" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Notes saisies</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<%
  Integer userId = (Integer) session.getAttribute("user");
  if (userId == null) {
    response.sendRedirect("index.jsp");
    return;
  }

  Map<Course, Map<Classes, List<Grade>>> courseClassGradeMap = (Map<Course, Map<Classes, List<Grade>>>) request.getAttribute("courseClassGradeMap");
%>
<jsp:include page="/elements/sidebar.jsp" />

<div>
  <h1>Note saisies</h1>
    <%
      if (courseClassGradeMap != null && !courseClassGradeMap.isEmpty()) {
    %>
      <div id="OldInfos">
    <%
        for (Map.Entry<Course, Map<Classes, List<Grade>>> courseEntry : courseClassGradeMap.entrySet()) {
          Course course = courseEntry.getKey();
          Map<Classes, List<Grade>> classGradeMap = courseEntry.getValue();
    %>
      <h2><%= course.getCourseName() %></h2>
      <%
        for (Map.Entry<Classes, List<Grade>> classEntry : classGradeMap.entrySet()) {
          Classes classe = classEntry.getKey();
          List<Grade> grades = classEntry.getValue();
      %>
        <h3><%= classe.getClassName() %></h3>
        <table>
          <tr>
            <th>Nom de la note</th>
            <th>Valeur de la note</th>
            <th>Coefficient de la note</th>
            <th>Nom et prénom de l'étudiant</th>
          </tr>
      <%
          for (Grade grade : grades) {
            Users student = UserDAO.getUserById(grade.getStudentId()); // Assuming you can fetch student info here
      %>
        <tr>
          <td><%= grade.getGradeName() %></td>
          <td><%= grade.getGradeValue() %></td>
          <td><%= grade.getGradeCoefficient() %></td>
          <td><%= student.getUserLastName() + " " + student.getUserName()  %></td>
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
    } else {
    %>
    <h2>Aucune note n'a été saisie</h2>
    <%
      }
    %>
</div>
</body>
</html>
