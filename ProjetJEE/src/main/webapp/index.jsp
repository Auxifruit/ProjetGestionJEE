<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.entities.Role" %>
<%@ page import="com.example.projetjee.model.entities.Users" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Accueil</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/styles.css">
  <style>
    body {
      margin: 0;
      padding: 0;
      font-family: Arial, sans-serif;
      background: linear-gradient(rgba(255, 255, 255, 0.8), rgba(255, 255, 255, 0.8)),
      url('css/banner.jpg');
      background-size: cover;
      background-attachment: fixed;
    }

    h1 {
      text-align: center;
      font-size: 50px;
      font-weight: 700;
      color: #202020;
      text-transform: uppercase;
      letter-spacing: 2px;
      margin-top: 40px;
      position: relative;
    }

    h1:before {
      position: absolute;
      content: "";
      left: 50%;
      bottom: -10px;
      width: 60px;
      height: 4px;
      margin-left: -30px;
      background-color: #dfdfdf;
    }

    h2 {
      text-align: center;
      font-size: 32px;
      font-weight: 600;
      color: #333;
      margin-top: 20px;
      position: relative;
    }

    h2:before {
      position: absolute;
      content: "";
      left: 50%;
      bottom: -10px;
      width: 50px;
      height: 3px;
      margin-left: -25px;
      background-color: #bbb;
    }

    .content {
      text-align: center;
      margin: 0 auto;
    }

    .video-container {
      margin-top: 20px;
      text-align: center;
    }

    iframe {
      max-width: 80%;
      border-radius: 8px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .links-container a {
      display: block;
      margin: 10px 0;
      font-size: 18px;
      color: #007BFF;
      text-decoration: none;
    }

    .links-container a:hover {
      text-decoration: underline;
    }
  </style>
</head>
<body>
<jsp:include page="elements/sidebar.jsp" />
<div class="content">
  <%
    Integer userId = (Integer) session.getAttribute("user");
    String greeting = "";
    if (userId != null) {
      Users user = UserDAO.getUserById(userId); // Récupération de l'utilisateur
      if (user != null) {
        greeting = "Bonjour, " + user.getUserName(); // Ajouter le prénom de l'utilisateur
      }
    }
  %>
  <h1>Bienvenue sur CY-EASE</h1>
  <h2><%= greeting %></h2>

  <div class="video-container">
    <iframe
            width="560"
            height="315"
            src="https://www.youtube.com/embed/duW6Er0VZZQ"
            frameborder="0"
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
            allowfullscreen>
    </iframe>
  </div>
  <h3>Cy-Ease, la plateforme innovante qui simplifie la gestion scolaire pour tous.</h3>
</div>
</body>
</html>
