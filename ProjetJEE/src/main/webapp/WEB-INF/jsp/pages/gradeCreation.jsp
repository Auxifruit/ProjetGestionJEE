<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.dao.CourseDAO" %>
<%@ page import="com.example.projetjee.model.entities.*" %>
<%@ page import="com.example.projetjee.model.dao.ClasseDAO" %><%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 12/11/2024
  Time: 19:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Création de note</title>
</head>
<body>
<h1>Création d'une nouvelle note</h1>
<%
    Integer userId = (Integer) session.getAttribute("user");
    if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
        response.sendRedirect("index.jsp");
        return;
    }

    List<Grade> gradeList = (List<Grade>) request.getAttribute("grades");
    List<Course> courseList = (List<Course>) request.getAttribute("courses");
    List<Student> studentList = (List<Student>) request.getAttribute("students");
    List<Teacher> teacherList = (List<Teacher>) request.getAttribute("teachers");

    if (gradeList == null || gradeList.isEmpty()) {
%>
<p>Il n'y a pas encore de note.</p>
<%
} else {
%>
<h3>Notes existante : </h3>
<table border="1">
    <th>Nom de la note</th>
    <th>Nom du cours</th>
    <th>Valeur de la note</th>
    <th>Coefficient de la note</th>
    <th>Nom et prénom de l'étudiant</th>
    <th>Nom et prénom de l'enseignant</th>
    <%
        for (Grade grade : gradeList) {
            Integer courseId = grade.getCourseId();
            Integer teacherId = grade.getTeacherId();
    %>
    <tr>
        <td><%= grade.getGradeName() %></td>
        <td>
            <% if(courseId == null) {
            %>
            Pas de cours associé
            <%
            } else {
            %>
            <%= CourseDAO.getCourseById(courseId).getCourseName() %>
            <%
                }
            %>
        </td>
        <td><%= grade.getGradeValue() %></td>
        <td><%= grade.getGradeCoefficient() %></td>
        <td><%= UserDAO.getUserById(grade.getStudentId()).getUserLastName() + " " + UserDAO.getUserById(grade.getStudentId()).getUserName() %></td>
        <td>
            <% if(teacherId == null) {
            %>
            Pas d'enseignant associé
            <%
            } else {
            %>
            <%= UserDAO.getUserById(teacherId).getUserLastName() + " " + UserDAO.getUserById(teacherId).getUserName() %>
            <%
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
%>
<h3>Nouvelle note :</h3>
<form action="gradeCreation-servlet" method="post">
    <label>Nom de la note : </label></br>
    <input type="text" name="newGradeName" required/></br></br>

    <label>Choix du cours : </label></br>
    <select name="newGradeCourseId">
        <%
            for(Course course : courseList) {
        %>
        <option value="<%= course.getCourseId() %>"><%= course.getCourseName() %></option>
        <%
            }
        %>
    </select></br></br>

    <label>Valeur de la note : </label></br>
    <input type="number" name="newGradeValue" required/></br></br>

    <label>Coefficient de la note : </label></br>
    <input type="number" name="newGradeCoefficient" required/></br></br>

    <label>Choix de l'étudiant : </label></br>
    <select name="newGradeStudentId">
        <%
            for(Student student : studentList) {
                Integer studentClassId = student.getClassId();
                if(studentClassId != null) {
        %>
        <option value="<%= student.getStudentId() %>"><%= UserDAO.getUserById(student.getStudentId()).getUserLastName() + " " + UserDAO.getUserById(student.getStudentId()).getUserName() + " " + ClasseDAO.getClasseById(studentClassId).getClassName() %></option>
        <%
                }
            }
        %>
    </select></br></br>

    <label>Choix de l'enseignant : </label></br>
    <select name="newGradeTeacherId">
        <%
            for(Teacher teacher : teacherList) {
        %>
        <option value="<%= teacher.getTeacherId() %>"><%= UserDAO.getUserById(teacher.getTeacherId()).getUserLastName() + " " + UserDAO.getUserById(teacher.getTeacherId()).getUserName()%></option>
        <%
            }
        %>
    </select></br></br>

    </br></br>
    <button type="submit" onclick="confirmCreate(event)">Créer</button>
</form>
<% String messageErreur = (String) request.getAttribute("erreur");
    if (messageErreur != null && !messageErreur.isEmpty()) {
%>
<p style='color: red'><%= messageErreur %>
</p></br>
<%
    }
%>
</body>
<script>
    function confirmCreate(event) {
        const confirmation = confirm("Êtes-vous sûr de vouloir créer la note ?");

        if (!confirmation) {
            event.preventDefault();
        }
    }
</script>
</html>
