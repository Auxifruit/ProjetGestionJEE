<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Inscription Étudiant</title>
</head>
<body>
<h1>Inscription Étudiant</h1>
<form action="${pageContext.request.contextPath}/inscription" method="post">
    <label for="nom">Nom :</label>
    <input type="text" id="nom" name="nom" required><br><br>

    <label for="prenom">Prénom :</label>
    <input type="text" id="prenom" name="prenom" required><br><br>

    <label for="email">Email :</label>
    <input type="email" id="email" name="email" required><br><br>

    <label for="password">Mot de passe :</label>
    <input type="password" id="password" name="password" required><br><br>

    <label for="dateNaissance">Date de naissance :</label>
    <input type="date" id="dateNaissance" name="dateNaissance" required><br><br>

    <label for="classe">Filière :</label>
    <select id="classe" name="classe" required>
        <option value="1">GSI1</option>
        <option value="2">GSI2</option>
        <option value="3">GSI3</option>
    </select><br><br>

    <button type="submit">S'inscrire</button>
</form>
</body>
</html>
