<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Tableau de bord</title>
</head>
<body>
<h1>Bienvenue, <%= request.getSession().getAttribute("user") %> !</h1>
<p>Vous êtes connecté avec succès.</p>
</body>
</html>
