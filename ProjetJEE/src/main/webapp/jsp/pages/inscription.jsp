<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Inscription</title>
</head>
<body>
<h2>Inscription</h2>
<form action="InscriptionServlet" method="post">
    Nom d'utilisateur: <input type="text" name="username" required><br>
    Mot de passe: <input type="password" name="password" required><br>
    Email: <input type="email" name="email" required><br>
    Rôle: <select name="role">
    <option value="etudiant">Étudiant</option>
    <option value="enseignant">Enseignant</option>
    <option value="admin">Administrateur</option>
</select><br>
    <input type="submit" value="S'inscrire">
</form>
</body>
</html>
