<%@ page import="com.example.projetjee.model.entities.Major" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Role" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Inscription</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<style>
    form {
        display: flex;
        flex-direction: column;
        gap: 10px;
        max-width: 550px;
        margin: 0 auto;
    }

    label {
        margin-bottom: 9px;
    }

    input, select, button {
        padding: 8px;
        font-size: 16px;
    }

    button {
        background-color: #283348;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    button:hover {
        background-color: #4a5672;
    }
</style>

<body>
<jsp:include page="/elements/sidebar.jsp" />
<form action="register" method="post" onsubmit="validateForm(event)">
    <h1>Inscription</h1>
    <label for="firstName">Prénom :</label>
    <input type="text" name="firstName" id="firstName" placeholder="Prénom" required>

    <label for="lastName">Nom :</label>
    <input type="text" name="lastName" id="lastName" placeholder="Nom" required>

    <label for="email">Email :</label>
    <input type="email" name="email" id="email" placeholder="Email exemple@xyz.com" required>

    <label for="password">Mot de passe :</label>
    <input type="password" name="password" id="password" required>

    <label for="birthdate">Date de naissance :</label>
    <input type="date" name="birthdate" id="birthdate" required>

    <label for="role">Choix du role :</label>
    <select type="date" name="role" id="role" onchange="toggleMajorChoice()" required>
        <option value=<%= Role.student %> selected="selected">Étudiant</option>
        <option value="<%= Role.teacher %>">Enseignant</option>
    </select></br>

    <%
        List<Major> majorList =(List<Major>) request.getAttribute("majors");

        if(majorList != null && !majorList.isEmpty()) {
    %>
    <label for="major" class="majorChoice">Filière :</label>
    <select name="major" id="major" class="majorChoice">
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

    <% if (request.getAttribute("error") != null) { %>
    <p style="color: red;"><%= request.getAttribute("error") %></p>
    <% } %>
    <% if (request.getAttribute("success") != null) { %>
    <p style="color: green;"><%= request.getAttribute("success") %></p>
    <% } %>

    <button type="submit">S'inscrire</button>

    <p style="display: flex; justify-content: center; align-items: center; text-align: center;">
        Vous avez déjà un compte ? <a href="${pageContext.request.contextPath}/login">Connectez-vous.</a>
    </p>
</form>
</body>
<script>
    function toggleMajorChoice() {
        const roleSelect = document.getElementById("role");
        const majorChoices = document.getElementsByClassName("majorChoice");
        const majorSelect = document.getElementById("major");

        if (roleSelect.value === "student") {
            for (let i = 0; i < majorChoices.length; i++) {
                majorChoices[i].style.display = "block";
            }
        } else {
            for (let i = 0; i < majorChoices.length; i++) {
                majorChoices[i].style.display = "none";
            }
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
