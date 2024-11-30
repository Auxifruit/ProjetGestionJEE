<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Connexion</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<form id="form" action="login" method="post">

    <center><a href="index.jsp"><img src="${pageContext.request.contextPath}/css/logo.png" alt="logo" style="width:120px;height:120px;"></a></center>
    </br></br>
    <input type="email" name="email" id="email" placeholder="Email" required><br>
    <input type="password" name="password" id="password" placeholder="Mot de passe" required><br>
    <% if (request.getAttribute("error") != null) { %>
    <p style="color: red;"><%= request.getAttribute("error") %></p><br>
    <% } %>
    <button type="submit">Se connecter</button>
    <p style="display: flex; justify-content: center; align-items: center; text-align: center;">
        Vous n'avez pas encore de compte ? <a href="${pageContext.request.contextPath}/register"> Inscrivez-vous.</a>
    </p>
</form>

</body>
</html>
