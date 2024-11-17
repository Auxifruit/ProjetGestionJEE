<%@ page import="java.util.List" %><%--
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
    }

    .subject-button:nth-child(1) { background-color: #a0c4ff; }
    .subject-button:nth-child(2) { background-color: #9ec4ff; }
    .subject-button:nth-child(3) { background-color: #8ec4ff; }

    .class-button:nth-child(1) { background-color: #80ed99; }
    .class-button:nth-child(2) { background-color: #ffef65; }
    .class-button:nth-child(3) { background-color: #89fc00; }
    .class-button:nth-child(4) { background-color: #9d4edd; }

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
</head>
<body>
<div class="container">
  <!-- matière -->
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

  <!-- classes -->
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

  <form action="/list-student-entry-note-servlet" method="POST" id="criteria-form">
    <input type="hidden" name="discipline" id="selected-discipline" value="">
    <input type="hidden" name="class" id="selected-class" value="">
    <button type="submit" id="submit-button">OK</button>
  </form>

  <hr>

  <!-- formulaire d'ajout de note -->
  <div class="form-section">
    <form action="HelloServlet" method="post">
      <button type="button" class="subject-button">+ Nouvelle note</button>

      <label>Intitulé du contrôle</label>
      <input type="text" name="intituleControle" class="input-field">

      <label>Choix Coefficient</label>
      <input type="text" name="coefficient" class="input-field">

      <label>Note de l'élève</label>
      <input type="text" name="note" class="input-field student-note">

      <label>Appréciation</label>
      <textarea name="appreciation" class="text-area"></textarea>

      <button type="submit" class="submit-button">Validez</button>
    </form>
  </div>

  <!-- liste des notes -->
  <div class="notes-table">
    <table border="1" cellpadding="10" cellspacing="0" width="100%">
      <tr>
        <th>Intitulé du contrôle</th>
        <th>Coefficient</th>
        <th>Note</th>
        <th>Appréciation</th>
      </tr>
      <!-- données -->
      <tr>
        <td>Contrôle 1</td>
        <td>1</td>
        <td>15/20</td>
        <td>Bien</td>
      </tr>
    </table>
  </div>
</div>
</body>
</html>

