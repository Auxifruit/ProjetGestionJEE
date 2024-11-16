<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.projetjee.model.entities.Utilisateur" %>
<%@ page import="com.example.projetjee.model.dao.UserDAO" %>

<%
    // Récupérer l'ID utilisateur transmis par la Servlet
    Integer userId = (Integer) request.getAttribute("userId");

    // Vérifier que l'ID utilisateur est valide
    if (userId == null) {
%>
<p style="color: red;">Erreur : aucun utilisateur connecté.</p>
<%
        return;
    }

    // Appeler la méthode DAO pour récupérer les données utilisateur
    Utilisateur user = UserDAO.getUserById(userId);

    // Vérifier que l'utilisateur existe
    if (user == null) {
%>
<p style="color: red;">Utilisateur introuvable.</p>
<%
        return;
    }
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
    </style>
    <script>
        // Utilisation des données utilisateur dans JavaScript
        let originalValues = {
            email: "<%= user.getEmailUtilisateur() %>",
            nom: "<%= user.getNomUtilisateur() %>",
            prenom: "<%= user.getPrenomUtilisateur() %>",
            dateNaissance: "<%= user.getDateDeNaissanceUtilisateur() %>",
            identifiant: "<%= user.getIdUtilisateur() %>",
            motDePasse: "<%= user.getMotDePasseUtilisateur() %>"
        };

        function toggleEditMode(isEditable) {
            document.getElementById("email").readOnly = !isEditable;
            document.getElementById("nom").readOnly = !isEditable;
            document.getElementById("prenom").readOnly = !isEditable;
            document.getElementById("identifiant").readOnly = !isEditable;
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
            document.getElementById("identifiant").value = originalValues.identifiant;
            document.getElementById("motDePasse").value = originalValues.motDePasse;
            document.getElementById("dateNaissance").value = originalValues.dateNaissance;
            toggleEditMode(false);
        }
    </script>
</head>
<body>
<h1>Modifier mes informations</h1>

<form action="modifierInformations" method="post">
    <label>Email :</label>
    <input type="text" id="email" name="email" value="<%= user.getEmailUtilisateur() %>" readonly>

    <label>Nom :</label>
    <input type="text" id="nom" name="nom" value="<%= user.getNomUtilisateur() %>" readonly>

    <label>Prénom :</label>
    <input type="text" id="prenom" name="prenom" value="<%= user.getPrenomUtilisateur() %>" readonly>

    <label>Date de naissance :</label>
    <input type="date" id="dateNaissance" name="dateNaissance" value="<%= user.getDateDeNaissanceUtilisateur() %>" readonly>

    <label>Identifiant :</label>
    <input type="text" id="identifiant" name="identifiant" value="<%= user.getIdUtilisateur() %>" readonly>

    <label>Mot de passe :</label>
    <div>
        <input type="password" id="motDePasse" name="motDePasse" value="<%= user.getMotDePasseUtilisateur() %>" readonly>
    </div>

    <!-- Boutons -->
    <button type="button" onclick="toggleEditMode(true)">Modifier</button>
    <button type="submit" style="display: none;">Enregistrer</button>
    <button type="button" style="display: none;" onclick="cancelEdit()">Annuler</button>
</form>
</body>
</html>
