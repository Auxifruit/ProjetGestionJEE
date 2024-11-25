<%@ page import="com.example.projetjee.model.entities.Major" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Role" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Inscription</title>
</head>
<body>
<h1>Inscription</h1>
<form action="register" method="post" onsubmit="validateForm(event)">
    <label for="firstName">Prénom :</label>
    <input type="text" name="firstName" id="firstName" required><br>

    <label for="lastName">Nom :</label>
    <input type="text" name="lastName" id="lastName" required><br>

    <label for="email">Email :</label>
    <input type="email" name="email" id="email" required><br>

    <label for="password">Mot de passe :</label>
    <input type="password" name="password" id="password" required><br>

    <label for="birthdate">Date de naissance :</label>
    <input type="date" name="birthdate" id="birthdate" required><br>

    <label for="role">Choix du role :</label>
    <select type="date" name="role" id="role" onchange="toggleMajorChoice()" required>
        <option value=<%= Role.student %> selected="selected">Étudiant</option>
        <option value="<%= Role.teacher %>">Enseignant</option>
    </select></br>

    <div id="majorChoice">
    <%
        List<Major> majorList =(List<Major>) request.getAttribute("majors");

        if(majorList != null && !majorList.isEmpty()) {
    %>
        <label for="major">Filière :</label>
        <select name="major" id="major">
            <option value="" selected="selected">Choisir une filière</option>
            <%
                for(Major major : majorList) {
            %>
            <option value="<%= major.getMajorId() %>"><%= major.getMajorName() %></option>
            <%
                }
            %>
        </select><br>
    <%
        }
    %>
    </div>

    <button type="submit">S'inscrire</button>
</form>

<% if (request.getAttribute("error") != null) { %>
<p style="color: red;"><%= request.getAttribute("error") %></p>
<% } %>
<% if (request.getAttribute("success") != null) { %>
<p style="color: green;"><%= request.getAttribute("success") %></p>
<% } %>
</body>
<script>
    function toggleMajorChoice() {
        const roleSelect = document.getElementById("role");
        const majorChoice = document.getElementById("majorChoice");
        const majorSelect = document.getElementById("major");

        if (roleSelect.value === "student") {
            majorChoice.style.display = "block";
        } else {
            majorChoice.style.display = "none";
            majorSelect.value = "";
        }
    }

    function validateForm(event) {
        const roleSelect = document.getElementById("role");
        const majorSelect = document.getElementById("major");
        if (roleSelect.value === "student" && majorSelect.value === "") {
            event.preventDefault(); // Empêche l'envoi du formulaire
            alert("Veuillez choisir une filière.");
        }
    }
</script>
</html>
