<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Index" %></h1>
<br/>
<a href="${pageContext.request.contextPath}/hello-servlet">Hello Servlet</a>
<br>
<a href="${pageContext.request.contextPath}/changeRole-servlet">Gestion des rôles</a>
<br>
<a href="${pageContext.request.contextPath}/lessonCreation-servlet">Creation d'une séance</a>
<br>
<a href="${pageContext.request.contextPath}/manageSubject-servlet">Gérer les matières</a>
<br>
<a href="${pageContext.request.contextPath}/course-registration-student-servlet">Inscription Etudiant</a>
<br>
<a href="${pageContext.request.contextPath}/entry-note-servlet">Saisie de note</a>
</body>
</html>