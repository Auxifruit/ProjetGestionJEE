<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %></h1>
<br/>
<a href="${pageContext.request.contextPath}/hello-servlet">Hello Servlet</a></br>
<a href="${pageContext.request.contextPath}/changeRole-servlet">Gestion des rôles</a></br>
<a href="${pageContext.request.contextPath}/manageLesson-servlet">Gérer les séances</a></br>
<a href="${pageContext.request.contextPath}/subjectManager-servlet">Gérer les matières</a></br>
<a href="${pageContext.request.contextPath}/courseManager-servlet">Gérer les cours</a></br>
<a href="${pageContext.request.contextPath}/assigningLessonsClasses-servlet">Assigner</a></br>
</body>
</html>