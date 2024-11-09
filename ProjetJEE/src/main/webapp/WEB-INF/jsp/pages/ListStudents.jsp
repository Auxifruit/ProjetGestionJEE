<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.Etudiant" %> <!-- Remplacez 'models.Etudiant' par 'com.example.projetjee.models.Etudiant' -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Élèves</title>
</head>
<body>
<h2>Liste des Élèves</h2>

<!-- Formulaire de filtrage -->
<form action="result.jsp" method="get">
    <label for="filterBy">Filtrer par :</label>
    <select name="filterBy" id="filterBy">
        <option value="">Sélectionnez un critère</option>
        <option value="prenom">Prénom</option>
        <option value="nom">Nom</option>
        <option value="email">Email</option>
        <option value="dateNaissance">Date de Naissance</option>
        <option value="classe">Classe</option>
    </select>

    <button type="submit">Valider</button>
</form>

<!-- Récupération de la liste des élèves depuis un attribut de requête ou de session -->
<%
    List<Etudiant> etudiants = (List<Etudiant>) request.getAttribute("students");
    if (etudiants == null) {
        etudiants = new ArrayList<>(); // Si la liste est nulle, on initialise une liste vide.
    }
%>

<table>
    <thead>
    <tr>
        <th>Prénom</th>
        <th>Nom</th>
        <th>Email</th>
        <th>Date de Naissance</th>
        <th>Classe</th>
    </tr>
    </thead>
    <tbody>
    <%
        if (etudiants.isEmpty()) {
    %>
    <tr>
        <td colspan="4">Aucun élève trouvé</td>
    </tr>
    <%
    } else {
        for (Etudiant etudiant : etudiants) {
    %>
    <tr>
        <td><%= etudiant.getPrénomEtudiant() %></td>
        <td><%= etudiant.getNomEtudiant() %></td>
        <td><%= etudiant.getEmailEtudiant() %></td>
        <td><%= etudiant.getDateDeNaissanceEtudiant() %></td>
        <td><%= etudiant.getIdClasse() %></td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>
</body>
</html>
