<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.projetjee.model.entities.Users" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="java.util.Date" %>

<%
  Integer userId = (Integer) session.getAttribute("user");
  if (userId == null) {
    response.sendRedirect("index.jsp");
    return;
  }

  // Appeler la méthode DAO pour récupérer les données utilisateur
  Users user = UserDAO.getUserById(userId);

  // Vérifier que l'utilisateur existe
  if (user == null) {
    response.sendRedirect("index.jsp");
    return;
  }
%>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Informations personnelles</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
  <style>
    label { font-weight: bold; }
    input[type="text"], input[type="password"], input[type="date"] {
      margin-bottom: 10px;
      width: 100%;
      padding: 5px;
    }
    .btn { padding: 8px 12px; cursor: pointer; margin-top: 10px; }
    .btn-modify { background-color: #4CAF50; color: white; }
    .btn-cancel { background-color: #f44336; color: white; }
  </style>
  <script>
    // Utilisation des données utilisateur dans JavaScript
    let originalValues = {
      email: "<%= user.getUserEmail() %>",
      nom: "<%= user.getUserLastName() %>",
      prenom: "<%= user.getUserName() %>",
      dateNaissance: "<%= user.getUserBirthdate() %>",
      motDePasse: "<%= user.getUserPassword() %>"
    };

    function toggleEditMode(isEditable) {
      document.getElementById("email").readOnly = !isEditable;
      document.getElementById("nom").readOnly = !isEditable;
      document.getElementById("prenom").readOnly = !isEditable;
      document.getElementById("motDePasse").readOnly = !isEditable;
      document.getElementById("dateNaissance").readOnly = !isEditable;
      document.getElementById("btnModify").style.display = isEditable ? "none" : "inline";
      document.getElementById("btnSave").style.display = isEditable ? "inline" : "none";
      document.getElementById("btnCancel").style.display = isEditable ? "inline" : "none";
    }

    function cancelEdit() {
      document.getElementById("email").value = originalValues.email;
      document.getElementById("nom").value = originalValues.nom;
      document.getElementById("prenom").value = originalValues.prenom;
      document.getElementById("motDePasse").value = originalValues.motDePasse;
      document.getElementById("dateNaissance").value = originalValues.dateNaissance;
      toggleEditMode(false);
    }
  </script>
</head>
<body>
<jsp:include page="/elements/sidebar.jsp" />

<form id="form" action="editInformations" method="post">
  <h1>Modifier mes informations</h1>
  <!-- Champ caché pour l'ID utilisateur -->
  <input type="hidden" id="userId" name="userId" value="<%= user.getUserId() %>">

  <label>Email :</label>
  <input type="text" id="email" name="email" value="<%= user.getUserEmail() %>" readonly>

  <label>Nom :</label>
  <input type="text" id="nom" name="nom" value="<%= user.getUserLastName() %>" readonly>

  <label>Prénom :</label>
  <input type="text" id="prenom" name="prenom" value="<%= user.getUserName() %>" readonly>

  <label>Date de naissance :</label>
  <input type="date" id="dateNaissance" name="dateNaissance" value="<%= user.getUserBirthdate() %>" readonly>

  <label>Mot de passe :</label>
  <div>
    <input type="password" id="motDePasse" name="motDePasse" value="<%= user.getUserPassword() %>" readonly>
  </div>

  <!-- Boutons -->
  <button type="button" id="btnModify" onclick="toggleEditMode(true)">Modifier</button>
  <button type="submit" id="btnSave" style="display: none;">Enregistrer</button>
  <button type="button" id="btnCancel" style="display: none;" onclick="cancelEdit()">Annuler</button>
</form>
</body>
</html>
