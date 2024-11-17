<form action="register" method="post">
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

    <label for="major">Filière :</label>
    <select name="major" id="major" required>
        <option value="" disabled selected>Choisir une filière</option>
        <option value="1">Génie Informatique</option>
        <option value="2">Génie Mécanique</option>
        <option value="3">Biotechnologie</option>
    </select><br>

    <button type="submit">S'inscrire</button>
</form>

<% if (request.getAttribute("error") != null) { %>
<p style="color: red;"><%= request.getAttribute("error") %></p>
<% } %>
<% if (request.getAttribute("success") != null) { %>
<p style="color: green;"><%= request.getAttribute("success") %></p>
<% } %>
