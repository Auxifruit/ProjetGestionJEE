<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Choisir cours disponible</title>
</head>
<body>
<jsp:include page="/elements/sidebar.jsp" />

<h3>Choisir cours disponible pour votre formation</h3>

<!-- Choix du cours  -->
<fieldset>
  <legend>Ingénieur informatique spécialité IA</legend>
  <table border="0" cellpadding="10">
    <tr>
      <td><input type="button" value="statistique" style="width: 100px; height: 50px;" /></td>
      <td><input type="button" value="IA renforcé" style="width: 100px; height: 50px;" /></td>
      <td><input type="button" value="Matrix" style="width: 100px; height: 50px;" /></td>
    </tr>
    <tr>
      <td><input type="button" value="Developpement web" style="width: 100px; height: 50px;" /></td>
      <td><input type="button" value="Design Pattern" style="width: 100px; height: 50px;" /></td>
      <td><input type="button" value="Visual computing" style="width: 100px; height: 50px;" /></td>
    </tr>
  </table>
</fieldset>

<!-- choix horaire -->
<fieldset>
  <legend>Créneaux disponible</legend>
  <table border="1" cellpadding="10" cellspacing="0">
    <tr>
      <td>Mardi 10h-12h</td>
    </tr>
    <tr>
      <td>Mercredi 12h-14h</td>
    </tr>
    <tr>
      <td>Jeudi 8h-10h</td>
    </tr>
    <tr>
      <td>Jeudi 14h-16h</td>
    </tr>
    <tr>
      <td>Vendredi 14h-16h</td>
    </tr>
    <tr>
      <td>Vendredi 16h-18h</td>
    </tr>
  </table>
</fieldset>

<!-- informations sur les cours -->
<fieldset>
  <legend>Information importante</legend>
  <table border="0" cellpadding="10" cellspacing="10">
    <tr>
      <td><span style="color: red;">cours necessaire: 2</span></td>
    </tr>
    <tr>
      <td><span style="color: blue;">cours disponible: 7</span></td>
    </tr>
  </table>
</fieldset>

</body>
</html>
