<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Inscription Réussie</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="/elements/sidebar.jsp" />
<div>
  <h1>Inscription réussie !</h1>
  <p>Nos administrateur doivent valider votre inscription avant de pouvoir vous connecter.</p>
  <a href="index.jsp"> <button>Retour index</button></a>
</div>
</body>
</html>

