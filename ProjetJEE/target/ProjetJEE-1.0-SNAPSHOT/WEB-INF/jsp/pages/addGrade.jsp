<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Users" %><%--
  Created by IntelliJ IDEA.
  User: Amaury
  Date: 15/11/2024
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Gestion des Notes</title>
  <link rel="stylesheet" type="text/css" href="${PageContext.request.contextPath}/Styles/SaisieNote.css">
  <style>
    body {
      font-family: Arial, sans-serif;
      display: flex;
      justify-content: center;
    }

    .container {
      width: 80%;
      margin: 20px;
      padding: 20px;
      border: 1px solid #ccc;
      border-radius: 8px;
      background-color: #f5f5f5;
    }

    .subject-buttons, .class-buttons {
      display: flex;
      gap: 10px;
      margin-bottom: 20px;
    }

    .subject-button, .class-button {
      padding: 10px;
      border-radius: 6px;
      color: white;
      border: none;
      cursor: pointer;
      background-color: #a0c4ff; /* Couleur bleue */
    }

    .subject-button.selected {
      background-color: #4a90e2; /* Couleur plus sombre lorsque sélectionné */
    }

    .class-button.selected {
      background-color: #4a90e2; /* Couleur plus sombre lorsque sélectionné */
    }

    /* Section formulaire pour ajouter une note */
    .form-section {
      display: flex;
      flex-direction: column;
      gap: 10px;
      margin-bottom: 20px;
    }

    .input-field, .text-area {
      width: 100%;
      padding: 8px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }

    .submit-button {
      padding: 10px;
      background-color: #32cd32;
      color: white;
      border: none;
      border-radius: 6px;
      cursor: pointer;
    }

    .notes-table, .note-entry, .student-note, .student-appreciation {
      margin-top: 20px;
    }

  </style>
  <script>
    function selectDiscipline(discipline) {
      // reset the selected state to the 'subject' buttons
      const buttons = document.querySelectorAll('.subject-button');
      buttons.forEach(button => button.classList.remove('selected'));

      // add selected state to the selected button
      const selectedButton = Array.from(buttons).find(btn => btn.textContent.trim() === discipline);
      if (selectedButton) {
        selectedButton.classList.add('selected');
      }

      // update hidden value of the form
      document.getElementById('selected-discipline').value = discipline;
    }

    function selectClass(className) {
      // reset select state to the 'classes' buttons
      const buttons = document.querySelectorAll('.class-button');
      buttons.forEach(button => button.classList.remove('selected'));

      // add selected state to the selected button
      const selectedButton = Array.from(buttons).find(btn => btn.textContent.trim() === className);
      if (selectedButton) {
        selectedButton.classList.add('selected');
      }

      // update hide value
      document.getElementById('selected-class').value = className;
    }
  </script>
</head>
<body>
<div class="container">

  <!-- Discipline -->
  <div class="subject-buttons">
    <%
      List<String> disciplines = (List<String>) request.getAttribute("disciplines");
      if (disciplines != null && !disciplines.isEmpty()) {
        for (String discipline : disciplines) {
    %>
    <button type="button" class="subject-button"
            onclick="selectDiscipline('<%= discipline %>')">
      <%= discipline %>
    </button>
    <%
      }
    } else {
    %>
    <p>pas de matière.</p>
    <% } %>
  </div>

  <!-- Classes -->
  <div class="class-buttons">
    <%
      List<String> classes = (List<String>) request.getAttribute("classes");
      if (classes != null && !classes.isEmpty()) {
        for (String className : classes) {
    %>
    <button type="button" class="class-button"
            onclick="selectClass('<%= className %>')">
      <%= className %>
    </button>
    <%
      }
    } else {
    %>
    <p>Pas de classe.</p>
    <%
      }
    %>
  </div>

  <!-- The OK button for the POST form to sent the criteria of the search -->
  <form action="${pageContext.request.contextPath}/entry-note-servlet" method="POST" id="criteria-form">
    <input type="hidden" name="discipline" id="selected-discipline" value="">
    <input type="hidden" name="class" id="selected-class" value="">
    <button type="submit" id="submit-button">OK</button>
  </form>

  <hr>

  <!-- Table to display students -->
  <form action="${pageContext.request.contextPath}/add-grade-servlet" method="post">
    <div class="form-section">
      <label>Intitulé du contrôle</label>
      <input type="text" name="gradeName" class="input-field" required>

      <label>Choix Coefficient</label>
      <input type="text" name="gradeCoefficient" class="input-field" required>
    </div>

    <div class="notes-table">
      <h3>Liste des étudiants</h3>
      <table style="width: 100%; border-collapse: collapse; text-align: left;">
        <thead>
        <tr>
          <th>Prénom</th>
          <th>Nom</th>
          <th>Email</th>
          <th>Note</th>
        </tr>
        </thead>
        <tbody>
        <%
          List<Users> students = (List<Users>) request.getAttribute("students");
          if (students != null && !students.isEmpty()) {
            for (Users student : students) {
        %>
        <tr>
          <td><%= student.getUserName() %></td>
          <td><%= student.getUserLastName() %></td>
          <td><%= student.getUserEmail() %></td>
          <td>
            <input type="number" name="grade_<%= student.getUserId() %>"
                   min="0" max="20" step="0.1" placeholder="Note" required>
          </td>
        </tr>
        <%
          }
        } else {
        %>
        <tr>
          <td colspan="4" style="text-align: center;">Aucun étudiant trouvé</td>
        </tr>
        <% } %>
        </tbody>
      </table>
    </div>

    <button type="submit" class="submit-button" style="margin-top: 20px;">Valider toutes les notes</button>
  </form>


  <!-- liste des notes
  <div class="notes-table">
    <table border="1" cellpadding="10" cellspacing="0" width="100%">
      <tr>
        <th>Intitulé du contrôle</th>
        <th>Coefficient</th>
        <th>Note</th>
        <th>Appréciation</th>
      </tr>
      données
      <tr>
        <td>Contrôle 1</td>
        <td>1</td>
        <td>15/20</td>
        <td>Bien</td>
      </tr>
    </table>
  </div>  -->
</div>
</body>
</html>

