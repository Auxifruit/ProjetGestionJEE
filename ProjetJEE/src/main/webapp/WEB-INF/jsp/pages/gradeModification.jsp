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
    <title>Modification de note</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="/elements/sidebar.jsp" />

<div>
    <h1>Modification d'une note</h1>
    <%
        Integer userId = (Integer) session.getAttribute("user");
        if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
            response.sendRedirect("index.jsp");
            return;
        }

        Grade grade = (Grade) request.getAttribute("grade");
        List<Course> courseList = (List<Course>) request.getAttribute("courses");
        List<Student> studentList = (List<Student>) request.getAttribute("students");
        List<Teacher> teacherList = (List<Teacher>) request.getAttribute("teachers");

        if (grade == null) {
    %>
    <p>La note n'existe pas</p>
    <%
    } else {
        Integer courseId = grade.getCourseId();
        Integer teacherId = grade.getTeacherId();
    %>
    <div id="OldInfos">
        <h3>Anciennes informations : </h3>
            <p>Nom de la note : <%= grade.getGradeName() %></p>
            <p>Nom du cours :
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
            </p>
            <p>Valeur de la note : <%= grade.getGradeValue() %></p>
            <p>Coefficient de la note : <%= grade.getGradeCoefficient() %></p>
            <p>Nom et prénom de l'étudiant : <%= UserDAO.getUserById(grade.getStudentId()).getUserLastName() + " " + UserDAO.getUserById(grade.getStudentId()).getUserName() %></p>
            <p>Nom et prénom de l'enseignant :
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
            </p>
            <%
                }
            %>
    </div>
    <form action="gradeModification-servlet" method="post">
        <h3>Nouvelles informations :</h3>
        <label>Nom de la note : </label>
        <input type="text" name="gradeNewName"/>

        <label>Choix du cours : </label>
        <select name="gradeNewCourseId">
            <option value="">Ne pas changer le cours</option>
            <%
                for(Course course : courseList) {
            %>
            <option value="<%= course.getCourseId() %>"><%= course.getCourseName() %></option>
            <%
                }
            %>
        </select>

        <label>Valeur de la note : </label>
        <input type="number" name="gradeNewValue"/>

        <label>Coefficient de la note : </label>
        <input type="number" name="gradeNewCoefficient"/>

        <label>Choix de l'étudiant : </label>
        <select name="gradeNewStudentId">
            <option value="">Ne pas changer l'étudiant</option>
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
        </select>

        <label>Choix de l'enseignant : </label>
        <select name="gradeNewTeacherId">
            <option value="">Ne pas changer l'enseignant</option>
            <%
                for(Teacher teacher : teacherList) {
            %>
            <option value="<%= teacher.getTeacherId() %>"><%= UserDAO.getUserById(teacher.getTeacherId()).getUserLastName() + " " + UserDAO.getUserById(teacher.getTeacherId()).getUserName()%></option>
            <%
                }
            %>
        </select>

        <input type="text" name="gradeId" value="<%= grade.getGradeId() %>" style="display: none">

        <% String messageErreur = (String) request.getAttribute("erreur");
            if (messageErreur != null && !messageErreur.isEmpty()) {
        %>
        <p style='color: red'><%= messageErreur %>
        </p>
        <%
            }
        %>
        <button type="submit" onclick="confirmModify(event)">Modifier</button>
    </form>
</div>
</body>
<script>
    function confirmModify(event) {
        const confirmation = confirm("Êtes-vous sûr de vouloir modifier la note ?");

        if (!confirmation) {
            event.preventDefault();
        }
    }
</script>
</html>
