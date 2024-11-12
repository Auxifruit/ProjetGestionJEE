<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %></h1>
<br/>
<a href="${pageContext.request.contextPath}/hello-servlet">Hello Servlet</a>
<a href="${pageContext.request.contextPath}/changeRole-servlet">Gestion des rôles</a>
<a href="${pageContext.request.contextPath}/lessonCreation-servlet">Creation d'une séance</a>
<a href="${pageContext.request.contextPath}/manageSubject-servlet">Gérer les matières</a>
</body>
</html>