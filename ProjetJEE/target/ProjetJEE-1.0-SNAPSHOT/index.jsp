<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<%
  Integer userId = (Integer) session.getAttribute("user");
%>
<h1><%= "Hello World!" %></h1>
<br/>
<a href="${pageContext.request.contextPath}/hello-servlet">Hello Servlet</a></br>
<a href="${pageContext.request.contextPath}/changeRole-servlet">Gestion des rôles</a></br>
<a href="${pageContext.request.contextPath}/lessonManager-servlet">Gérer les séances</a></br>
<a href="${pageContext.request.contextPath}/subjectManager-servlet">Gérer les matières</a></br>
<a href="${pageContext.request.contextPath}/courseManager-servlet">Gérer les cours</a></br>
<a href="${pageContext.request.contextPath}/studentSchedule-servlet?studentId=2">Emplois du temps de l'étudiant n°2</a></br>
<a href="${pageContext.request.contextPath}/login">Connexion</a></br>
<a href="${pageContext.request.contextPath}/register">Inscription</a></br>
<%
  if(userId != null) {
%>
  <a href="${pageContext.request.contextPath}/LogoutServlet">Deconexion</a></br>
<%
  }
%>
</body>
</html>