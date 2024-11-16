<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Connexion</title>
</head>
<body>
<h1>Connexion</h1>
<form action="login" method="post">
    <label for="email">Email :</label>
    <input type="email" name="email" id="email" required><br>
    <label for="password">Mot de passe :</label>
    <input type="password" name="password" id="password" required><br>
    <button type="submit">Se connecter</button>
</form>
<% if (request.getAttribute("error") != null) { %>
<p style="color: red;"><%= request.getAttribute("error") %></p>
<% } %>
</body>
</html>
