<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>

<%
    // Informations de l'utilisateur
    String email = (String) session.getAttribute("email");
    String nom = (String) session.getAttribute("nom");
    String prenom = (String) session.getAttribute("prenom");
    String dateNaissance = (String) session.getAttribute("dateNaissance");
    String identifiant = (String) session.getAttribute("identifiant");
    String motDePasse = (String) session.getAttribute("motDePasse");
    String role = (String) session.getAttribute("role");
%>

<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Informations personnelles</title>
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
        .password-container { display: flex; align-items: center; }
        .eye-icon { margin-left: 5px; cursor: pointer; }
    </style>
    <script>
        let originalValues = {
            email: "<%= email %>", nom: "<%= nom %>", prenom: "<%= prenom %>",
            dateNaissance: "<%= dateNaissance %>", identifiant: "<%= identifiant %>", motDePasse: "<%= motDePasse %>"
        };

        function toggleEditMode(isEditable) {
            document.getElementById("email").readOnly = !isEditable;
            document.getElementById("nom").readOnly = !isEditable;
            document.getElementById("prenom").readOnly = !isEditable;
            document.getElementById("identifiant").readOnly = !isEditable;
            document.getElementById("motDePasse").readOnly = !isEditable;
            <% if (!"admin".equals(role)) { %>
            document.getElementById("dateNaissance").readOnly = !isEditable;
            <% } %>
            document.getElementById("btnModify").style.display = isEditable ? "none" : "inline";
            document.getElementById("btnSave").style.display = isEditable ? "inline" : "none";
            document.getElementById("btnCancel").style.display = isEditable ? "inline" : "none";
        }

        function cancelEdit() {
            document.getElementById("email").value = originalValues.email;
            document.getElementById("nom").value = originalValues.nom;
            document.getElementById("prenom").value = originalValues.prenom;
            document.getElementById("identifiant").value = originalValues.identifiant;
            document.getElementById("motDePasse").value = originalValues.motDePasse;
            <% if (!"admin".equals(role)) { %>
            document.getElementById("dateNaissance").value = originalValues.dateNaissance;
            <% } %>
            toggleEditMode(false);
        }

        function togglePasswordVisibility() {
            const passwordField = document.getElementById("motDePasse");
            const eyeIcon = document.getElementById("eyeIcon");
            if (passwordField.type === "password") {
                passwordField.type = "text";
                eyeIcon.textContent = "🙈"; // Icône pour masquer le mot de passe
            } else {
                passwordField.type = "password";
                eyeIcon.textContent = "👁️"; // Icône pour afficher le mot de passe
            }
        }
    </script>
</head>
<body onload="toggleEditMode(false)">
<h1>Modifier mes informations</h1>

<!-- Formulaire de modification des informations -->
<form action="modifierInformations" method="post">
    <label>Email :</label>
    <input type="text" id="email" name="email" value="<%= email %>" readonly>

    <label>Nom :</label>
    <input type="text" id="nom" name="nom" value="<%= nom %>" readonly>

    <label>Prénom :</label>
    <input type="text" id="prenom" name="prenom" value="<%= prenom %>" readonly>

    <% if (!"admin".equals(role)) { %>
    <label>Date de naissance :</label>
    <input type="date" id="dateNaissance" name="dateNaissance" value="<%= dateNaissance %>" readonly>
    <% } %>

    <label>Identifiant :</label>
    <input type="text" id="identifiant" name="identifiant" value="<%= identifiant %>" readonly>

    <label>Mot de passe :</label>
    <div class="password-container">
        <input type="password" id="motDePasse" name="motDePasse" value="<%= motDePasse %>" readonly>
        <span id="eyeIcon" class="eye-icon" onclick="togglePasswordVisibility()">👁️</span>
    </div>

    <label>Rôle utilisateur :</label>
    <input type="text" id="role" name="role" value="<%= role %>" readonly>

    <!-- Boutons de modification, enregistrement et annulation -->
    <button type="button" id="btnModify" class="btn btn-modify" onclick="toggleEditMode(true)">Modifier</button>
    <button type="submit" id="btnSave" class="btn btn-modify" style="display: none;">Enregistrer</button>
    <button type="button" id="btnCancel" class="btn btn-cancel" style="display: none;" onclick="cancelEdit()">Annuler</button>
</form>

</body>
</html>
