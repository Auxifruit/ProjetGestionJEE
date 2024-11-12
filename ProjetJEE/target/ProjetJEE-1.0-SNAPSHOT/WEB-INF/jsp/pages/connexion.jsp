<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Connexion</title>
</head>
<body>
<h2>Connexion</h2>
<form action="ConnexionServlet" method="post">
  Nom d'utilisateur: <input type="text" name="username" required><br>
  Mot de passe: <input type="password" name="password" required><br>
  <input type="submit" value="Se connecter">
</form>
</body>
</html>
